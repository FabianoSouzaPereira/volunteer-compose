package com.fabianospdev.volunteerscompose.features.login.presentation

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabianospdev.volunteerscompose.core.helpers.retry.RetryController
import com.fabianospdev.volunteerscompose.features.login.domain.usecases.LoginUsecase
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUsecase: LoginUsecase,
    private val retryController: RetryController,
) : ViewModel() {
    private val _state = MutableStateFlow<LoginState>(LoginState.LoginIdle)
    val state: StateFlow<LoginState> = _state

    var username = mutableStateOf("")
    var password = mutableStateOf("")
    var usernameError = mutableStateOf<String?>(null)
    var passwordError = mutableStateOf<String?>(null)

    var isFormValid = derivedStateOf { validateForm() }
    var isPasswordVisible = mutableStateOf(false)

    private val _showRetryLimitReached = MutableStateFlow(false)
    val showRetryLimitReached: StateFlow<Boolean> get() = _showRetryLimitReached

    private fun validateForm(): Boolean {
        var isValid = true

        usernameError.value = null
        passwordError.value = null

        if (username.value.isBlank()) {
            usernameError.value = "E-mail é obrigatório"
            isValid = false
        } else if (!username.value.contains("@")) {
            usernameError.value = "E-mail inválido"
            isValid = false
        }

        if (password.value.isBlank()) {
            passwordError.value = "Senha é obrigatória"
            isValid = false
        } else if (password.value.length < 6) {
            passwordError.value = "Senha muito curta"
            isValid = false
        }

        return isValid
    }

    private fun performLogin(email: String, password: String) {
        if (!retryController.isRetryEnabled.value) {
            _showRetryLimitReached.value = true
            return
        }

        if (email.isBlank() || password.isBlank()) {
            _state.value = LoginState.LoginValidationError("E-mail e senha são obrigatórios")
            return
        }

        _state.value = LoginState.LoginLoading

        viewModelScope.launch {
            _state.value = LoginState.LoginLoading

            val result = loginUsecase.getLogin(email, password)

            _state.value = result.fold(
                onSuccess = { response ->
                    retryController.resetRetryCount()
                    clearInputFields()
                    LoginState.LoginSuccess(response)
                },
                onFailure = { throwable ->
                    retryController.incrementRetryCount()
                    val message = throwable.message ?: "Erro desconhecido"

                    when {
                        message.contains("timeout", ignoreCase = true) ->
                            LoginState.LoginTimeoutError(message)

                        message.contains("unauthorized", ignoreCase = true) ||
                                message.contains("401") ->
                            LoginState.LoginUnauthorized(message)

                        message.contains("network", ignoreCase = true) ||
                                message.contains("unable to resolve host", ignoreCase = true) ->
                            LoginState.LoginNoConnection(message)

                        else -> LoginState.LoginError(message)
                    }
                }
            )
        }
    }

    fun onLoginClick() {
        if (validateForm()) {
            performLogin(username.value, password.value)
        }
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
        passwordError.value = null
    }

    fun onUsernameChange(newUsername: String) {
        username.value = newUsername
        usernameError.value = null
    }

    fun onTogglePasswordVisibility() {
        isPasswordVisible.value = !isPasswordVisible.value
    }

    fun resetState() {
        _state.value = LoginState.LoginIdle
    }

    fun onRetry() {
        resetState()
    }

    fun clearInputFields() {
        username.value = ""
        password.value = ""
        usernameError.value = null
        passwordError.value = null
    }

    fun resetAll() {
        clearInputFields()
        _showRetryLimitReached.value = false
        resetState()
    }
}
package com.fabianospdev.volunteerscompose.features.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabianospdev.volunteerscompose.core.di.DispatcherProvider
import com.fabianospdev.volunteerscompose.core.helpers.retry.RetryController
import com.fabianospdev.volunteerscompose.features.login.domain.entities.LoginResponseEntity
import com.fabianospdev.volunteerscompose.features.login.domain.usecases.LoginUsecase
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginNavigationEvent
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginState
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUsecase: LoginUsecase,
    private val retryController: RetryController,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _viewState = MutableStateFlow(LoginViewState())
    val viewState: StateFlow<LoginViewState> = _viewState.asStateFlow()

    private val _showRetryLimitReached = MutableStateFlow(false)
    val showRetryLimitReached: StateFlow<Boolean> get() = _showRetryLimitReached


    private val _navigationEvents = MutableSharedFlow<LoginNavigationEvent>()
    val navigationEvents: SharedFlow<LoginNavigationEvent> = _navigationEvents.asSharedFlow()

    init {
        observeRetryController()
    }

    private fun validateForm(): Boolean {
        val form = _viewState.value.formState

        val usernameRules = listOf(
            ValidationRule(
                condition = form.username.isNotBlank(),
                errorMessage = "E-mail é obrigatório"
            ),
            ValidationRule(
                condition = form.username.contains("@"),
                errorMessage = "E-mail inválido"
            ),
            ValidationRule(
                condition = form.username.length <= 100,
                errorMessage = "E-mail muito longo"
            )
        )

        val passwordRules = listOf(
            ValidationRule(
                condition = form.password.isNotBlank(),
                errorMessage = "Senha é obrigatória"
            ),
            ValidationRule(
                condition = form.password.length >= 6,
                errorMessage = "Senha deve ter pelo menos 6 caracteres"
            ),
            ValidationRule(
                condition = form.password.length <= 50,
                errorMessage = "Senha muito longa"
            )
        )

        val usernameError = usernameRules.firstOrNull { !it.condition }?.errorMessage
        val passwordError = passwordRules.firstOrNull { !it.condition }?.errorMessage

        _viewState.update { state ->
            state.copy(
                formState = state.formState.copy(
                    usernameError = usernameError,
                    passwordError = passwordError,
                    isFormValid = loginButtonActive()
                ),
                screenState = if (usernameError != null || passwordError != null) {
                    LoginState.LoginValidationError("Verifique os campos destacados")
                } else {
                    LoginState.LoginIdle
                }
            )
        }

        return usernameError == null && passwordError == null
    }

    /** Data class to validations events */
    private data class ValidationRule(
        val condition: Boolean,
        val errorMessage: String
    )

    private fun observeRetryController() {
        viewModelScope.launch {
            retryController.isRetryLimitReached.collect { reached ->
                _showRetryLimitReached.value = reached
            }
        }
    }

    private fun performLogin(email: String, password: String) {
        if (!retryController.isRetryEnabled.value) {
            return
        }

        _viewState.update { it.copy(screenState = LoginState.LoginLoading) }

        viewModelScope.launch(dispatcherProvider.io) {
            val result = loginUsecase.getLogin(email, password)

            result.fold(
                onSuccess = { response ->
                    retryController.resetRetryCount()
                    clearInputFields()
                    onLoginSuccessWithDelay(response)
                },
                onFailure = { throwable ->
                    retryController.incrementRetryCount()
                    val message = throwable.message ?: "Erro desconhecido"

                    val errorState = when {
                        message.contains("timeout", ignoreCase = true) ->
                            LoginState.LoginTimeoutError(message)
                        message.contains("network", ignoreCase = true) ||
                                message.contains("unable to resolve host", ignoreCase = true) ->
                            LoginState.LoginNoConnection(message)
                        message.contains("unauthorized", ignoreCase = true) ||
                                message.contains("401") ->
                            LoginState.LoginUnauthorized(message)
                        else -> LoginState.LoginError(message)
                    }

                    _viewState.update { it.copy(screenState = errorState) }
                }
            )
        }
    }

    fun onLoginClick() {
        if (validateForm()) {
            performLogin(
                _viewState.value.formState.username,
                _viewState.value.formState.password
            )
        }
    }

    fun onLoginSuccessWithDelay(response: LoginResponseEntity) {
        viewModelScope.launch {
            _viewState.update { it.copy(screenState = LoginState.LoginSuccess(response)) }
            delay(3000)
            _navigationEvents.emit(LoginNavigationEvent.NavigateToHome)
        }
    }

    // Outros eventos de navegação
    fun onNavigateToHome() {
        viewModelScope.launch {
            _navigationEvents.emit(LoginNavigationEvent.NavigateToHome)
        }
    }

    fun onNavigateToSettings() {
        viewModelScope.launch {
            _navigationEvents.emit(LoginNavigationEvent.NavigateToSettings)
        }
    }

    fun onUsernameChange(newUsername: String) {
        _viewState.update { state ->
            state.copy(
                formState = state.formState.copy(
                    username = newUsername,
                    usernameError = null,
                    isFormValid = loginButtonActive()
                )
            )
        }
    }

    fun onPasswordChange(newPassword: String) {
        _viewState.update { state ->
            state.copy(
                formState = state.formState.copy(
                    password = newPassword,
                    passwordError = null,
                    isFormValid = loginButtonActive()
                )
            )
        }
    }

    fun loginButtonActive(): Boolean {
        val form = _viewState.value.formState
        return form.username.isNotBlank() && form.password.isNotBlank()
    }

    fun onTogglePasswordVisibility() {
        _viewState.update { state ->
            state.copy(
                formState = state.formState.copy(
                    showPassword = !state.formState.showPassword
                )
            )
        }
    }

    fun onRetry() {
        if (retryController.isRetryEnabled.value) {
            performLogin(
                _viewState.value.formState.username,
                _viewState.value.formState.password
            )
        }
    }

    fun resetState() {
        _viewState.update { it.copy(screenState = LoginState.LoginIdle) }
    }

    fun clearInputFields() {
        _viewState.update { state ->
            state.copy(
                formState = state.formState.copy(
                    username = "",
                    password = "",
                    usernameError = null,
                    passwordError = null,
                    isFormValid = false
                )
            )
        }
    }

    fun resetAll() {
        clearInputFields()
        retryController.resetRetryLimitNotification()
        resetState()
    }
}
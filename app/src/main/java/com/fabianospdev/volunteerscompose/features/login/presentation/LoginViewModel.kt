package com.fabianospdev.volunteerscompose.features.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabianospdev.volunteerscompose.core.di.DispatcherProvider
import com.fabianospdev.volunteerscompose.core.helpers.TokenManager
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.BadRequestException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.NetworkException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.TimeoutException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.UnauthorizedException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.ValidationException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.errorMessage
import com.fabianospdev.volunteerscompose.core.helpers.mutableNavigationEvents
import com.fabianospdev.volunteerscompose.core.helpers.retry.RetryController
import com.fabianospdev.volunteerscompose.features.login.domain.entities.LoginResponseEntity
import com.fabianospdev.volunteerscompose.features.login.domain.usecases.LoginUsecase
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginNavigationEvent
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginState
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUsecase: LoginUsecase,
    private val retryController: RetryController,
    private val dispatcherProvider: DispatcherProvider,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _viewState = MutableStateFlow(LoginViewState())
    val viewState: StateFlow<LoginViewState> = _viewState.asStateFlow()

    private val _showRetryLimitReached = MutableStateFlow(false)
    val showRetryLimitReached: StateFlow<Boolean> get() = _showRetryLimitReached

    private val _navigationEvents = mutableNavigationEvents<LoginNavigationEvent>()
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
                    isFormValid = usernameError == null && passwordError == null
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
                    tokenManager.saveToken(response.token)
                    clearInputFields()
                    onLoginSuccessWithDelay(response)
                },
                onFailure = { throwable ->
                    retryController.incrementRetryCount()
                    _viewState.update { it.copy(screenState = throwable.toLoginErrorState()) }
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

    fun onNavigateToForgotPassword() {
        viewModelScope.launch {
            _navigationEvents.emit(LoginNavigationEvent.NavigateToForgotPassword)
        }
    }

    fun onNavigateToRegister() {
        viewModelScope.launch {
            _navigationEvents.emit(LoginNavigationEvent.NavigateToRegister)
        }
    }

    fun onUsernameChange(newUsername: String) {
        _viewState.update { state ->
            state.copy(
                formState = state.formState.copy(
                    username = newUsername,
                    usernameError = null
                )
            )
        }
    }

    fun onPasswordChange(newPassword: String) {
        _viewState.update { state ->
            state.copy(
                formState = state.formState.copy(
                    password = newPassword,
                    passwordError = null
                )
            )
        }
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

private fun Throwable.toLoginErrorState(): LoginState {
    val message = errorMessage()
    return when (this) {
        is TimeoutException -> LoginState.LoginTimeoutError(message)
        is NetworkException -> LoginState.LoginNoConnection(message)
        is UnauthorizedException -> LoginState.LoginUnauthorized(message)
        is BadRequestException, is ValidationException -> LoginState.LoginValidationError(message)
        else -> LoginState.LoginError(message)
    }
}

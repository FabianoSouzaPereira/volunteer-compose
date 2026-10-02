package com.fabianospdev.volunteerscompose.features.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabianospdev.volunteerscompose.core.di.DispatcherProvider
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.BadRequestException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.NetworkException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.TimeoutException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.UnauthorizedException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.ValidationException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.errorMessage
import com.fabianospdev.volunteerscompose.core.helpers.mutableNavigationEvents
import com.fabianospdev.volunteerscompose.features.register.domain.usecases.RegisterUseCase
import com.fabianospdev.volunteerscompose.features.register.presentation.states.RegisterFormState
import com.fabianospdev.volunteerscompose.features.register.presentation.states.RegisterNavigationEvent
import com.fabianospdev.volunteerscompose.features.register.presentation.states.RegisterState
import com.fabianospdev.volunteerscompose.features.register.presentation.states.RegisterViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _viewState = MutableStateFlow(RegisterViewState())
    val viewState: StateFlow<RegisterViewState> = _viewState.asStateFlow()

    private val _navigationEvents = mutableNavigationEvents<RegisterNavigationEvent>()
    val navigationEvents: SharedFlow<RegisterNavigationEvent> = _navigationEvents.asSharedFlow()

    fun onNameChange(name: String) {
        updateForm { it.copy(name = name, nameError = null) }
    }

    fun onEmailChange(email: String) {
        updateForm { it.copy(email = email, emailError = null) }
    }

    fun onPasswordChange(password: String) {
        updateForm { it.copy(password = password, passwordError = null) }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        updateForm { it.copy(confirmPassword = confirmPassword, confirmPasswordError = null) }
    }

    fun onTogglePasswordVisibility() {
        updateForm { it.copy(showPassword = !it.showPassword) }
    }

    fun onSubmit() {
        val form = validatedForm() ?: return
        register(form.name, form.email, form.password)
    }

    fun onRetry() {
        val form = _viewState.value.formState
        if (form.nameErrorMessage() == null &&
            form.emailErrorMessage() == null &&
            form.passwordErrorMessage() == null
        ) {
            register(form.name, form.email, form.password)
        }
    }

    fun onNavigateBack() {
        viewModelScope.launch {
            _navigationEvents.emit(RegisterNavigationEvent.NavigateBack)
        }
    }

    fun onNavigateToLogin() {
        viewModelScope.launch {
            _navigationEvents.emit(RegisterNavigationEvent.NavigateToLogin)
        }
    }

    private fun validatedForm(): RegisterFormState? {
        val form = _viewState.value.formState
        val nameError = form.nameErrorMessage()
        val emailError = form.emailErrorMessage()
        val passwordError = form.passwordErrorMessage()
        val confirmPasswordError = form.confirmPasswordErrorMessage()
        val updated = form.copy(
            nameError = nameError,
            emailError = emailError,
            passwordError = passwordError,
            confirmPasswordError = confirmPasswordError
        )
        _viewState.update { it.copy(formState = updated) }
        val isValid = nameError == null &&
            emailError == null &&
            passwordError == null &&
            confirmPasswordError == null
        return updated.takeIf { isValid }
    }

    private fun register(name: String, email: String, password: String) {
        _viewState.update { it.copy(screenState = RegisterState.RegisterLoading) }

        viewModelScope.launch(dispatcherProvider.io) {
            registerUseCase.register(name, email, password).fold(
                onSuccess = { response ->
                    _viewState.update {
                        it.copy(screenState = RegisterState.RegisterSuccess(response))
                    }
                },
                onFailure = { throwable ->
                    _viewState.update { it.copy(screenState = throwable.toRegisterErrorState()) }
                }
            )
        }
    }

    private fun updateForm(transform: (RegisterFormState) -> RegisterFormState) {
        _viewState.update { it.copy(formState = transform(it.formState)) }
    }
}

private fun Throwable.toRegisterErrorState(): RegisterState {
    val message = errorMessage()
    return when (this) {
        is TimeoutException -> RegisterState.RegisterTimeoutError(message)
        is NetworkException -> RegisterState.RegisterNoConnection(message)
        is UnauthorizedException -> RegisterState.RegisterUnauthorized(message)
        is BadRequestException, is ValidationException -> RegisterState.RegisterValidationError(message)
        else -> RegisterState.RegisterError(message)
    }
}

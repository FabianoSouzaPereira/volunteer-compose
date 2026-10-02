package com.fabianospdev.volunteerscompose.features.forgotpassword.presentation

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
import com.fabianospdev.volunteerscompose.features.forgotpassword.domain.usecases.ForgotPasswordUseCase
import com.fabianospdev.volunteerscompose.features.forgotpassword.presentation.states.ForgotPasswordNavigationEvent
import com.fabianospdev.volunteerscompose.features.forgotpassword.presentation.states.ForgotPasswordState
import com.fabianospdev.volunteerscompose.features.forgotpassword.presentation.states.ForgotPasswordViewState
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
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _viewState = MutableStateFlow(ForgotPasswordViewState())
    val viewState: StateFlow<ForgotPasswordViewState> = _viewState.asStateFlow()

    private val _navigationEvents = mutableNavigationEvents<ForgotPasswordNavigationEvent>()
    val navigationEvents: SharedFlow<ForgotPasswordNavigationEvent> =
        _navigationEvents.asSharedFlow()

    fun onEmailChange(email: String) {
        _viewState.update { state ->
            state.copy(formState = state.formState.copy(email = email, emailError = null))
        }
    }

    fun onSubmit() {
        if (validateForm()) {
            requestReset(_viewState.value.formState.email)
        }
    }

    fun onRetry() {
        if (validateForm()) {
            requestReset(_viewState.value.formState.email)
        }
    }

    fun onNavigateBack() {
        viewModelScope.launch {
            _navigationEvents.emit(ForgotPasswordNavigationEvent.NavigateBack)
        }
    }

    fun onNavigateToLogin() {
        viewModelScope.launch {
            _navigationEvents.emit(ForgotPasswordNavigationEvent.NavigateToLogin)
        }
    }

    private fun validateForm(): Boolean {
        val emailError = _viewState.value.formState.emailErrorMessage()
        _viewState.update { state ->
            state.copy(formState = state.formState.copy(emailError = emailError))
        }
        return emailError == null
    }

    private fun requestReset(email: String) {
        _viewState.update { it.copy(screenState = ForgotPasswordState.ForgotPasswordLoading) }

        viewModelScope.launch(dispatcherProvider.io) {
            forgotPasswordUseCase.requestPasswordReset(email).fold(
                onSuccess = { response ->
                    _viewState.update {
                        it.copy(screenState = ForgotPasswordState.ForgotPasswordSuccess(response))
                    }
                },
                onFailure = { throwable ->
                    _viewState.update {
                        it.copy(screenState = throwable.toForgotPasswordErrorState())
                    }
                }
            )
        }
    }
}

private fun Throwable.toForgotPasswordErrorState(): ForgotPasswordState {
    val message = errorMessage()
    return when (this) {
        is TimeoutException -> ForgotPasswordState.ForgotPasswordTimeoutError(message)
        is NetworkException -> ForgotPasswordState.ForgotPasswordNoConnection(message)
        is UnauthorizedException -> ForgotPasswordState.ForgotPasswordUnauthorized(message)
        is BadRequestException, is ValidationException ->
            ForgotPasswordState.ForgotPasswordValidationError(message)
        else -> ForgotPasswordState.ForgotPasswordError(message)
    }
}

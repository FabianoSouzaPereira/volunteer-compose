package com.fabianospdev.volunteerscompose.features.forgotpassword.presentation.states

import com.fabianospdev.volunteerscompose.core.domain.models.ErrorType
import com.fabianospdev.volunteerscompose.features.forgotpassword.domain.entities.ForgotPasswordResponseEntity

sealed class ForgotPasswordState {
    object ForgotPasswordLoading : ForgotPasswordState()
    object ForgotPasswordIdle : ForgotPasswordState()
    data class ForgotPasswordSuccess(val response: ForgotPasswordResponseEntity) : ForgotPasswordState()
    data class ForgotPasswordError(val error: String) : ForgotPasswordState()
    data class ForgotPasswordNoConnection(val errorMessage: String) : ForgotPasswordState()
    data class ForgotPasswordValidationError(val message: String) : ForgotPasswordState()
    data class ForgotPasswordTimeoutError(val message: String) : ForgotPasswordState()
    data class ForgotPasswordUnauthorized(val message: String) : ForgotPasswordState()
    data class ForgotPasswordUnknown(val message: String) : ForgotPasswordState()
}

fun ForgotPasswordState.toErrorType(): ErrorType = when (this) {
    is ForgotPasswordState.ForgotPasswordNoConnection -> ErrorType.NETWORK
    is ForgotPasswordState.ForgotPasswordTimeoutError -> ErrorType.TIMEOUT
    is ForgotPasswordState.ForgotPasswordUnauthorized -> ErrorType.UNAUTHORIZED
    is ForgotPasswordState.ForgotPasswordValidationError -> ErrorType.VALIDATION
    else -> ErrorType.UNKNOWN
}

fun ForgotPasswordState.errorText(): String = when (this) {
    is ForgotPasswordState.ForgotPasswordError -> error
    is ForgotPasswordState.ForgotPasswordNoConnection -> errorMessage
    is ForgotPasswordState.ForgotPasswordTimeoutError -> message
    is ForgotPasswordState.ForgotPasswordUnauthorized -> message
    is ForgotPasswordState.ForgotPasswordValidationError -> message
    is ForgotPasswordState.ForgotPasswordUnknown -> message
    else -> ""
}

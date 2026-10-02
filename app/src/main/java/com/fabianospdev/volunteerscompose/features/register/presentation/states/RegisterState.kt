package com.fabianospdev.volunteerscompose.features.register.presentation.states

import com.fabianospdev.volunteerscompose.core.domain.models.ErrorType
import com.fabianospdev.volunteerscompose.features.register.domain.entities.RegisterResponseEntity

sealed class RegisterState {
    object RegisterLoading : RegisterState()
    object RegisterIdle : RegisterState()
    data class RegisterSuccess(val response: RegisterResponseEntity) : RegisterState()
    data class RegisterError(val error: String) : RegisterState()
    data class RegisterNoConnection(val errorMessage: String) : RegisterState()
    data class RegisterValidationError(val message: String) : RegisterState()
    data class RegisterTimeoutError(val message: String) : RegisterState()
    data class RegisterUnauthorized(val message: String) : RegisterState()
    data class RegisterUnknown(val message: String) : RegisterState()
}

fun RegisterState.toErrorType(): ErrorType = when (this) {
    is RegisterState.RegisterNoConnection -> ErrorType.NETWORK
    is RegisterState.RegisterTimeoutError -> ErrorType.TIMEOUT
    is RegisterState.RegisterUnauthorized -> ErrorType.UNAUTHORIZED
    is RegisterState.RegisterValidationError -> ErrorType.VALIDATION
    else -> ErrorType.UNKNOWN
}

fun RegisterState.errorText(): String = when (this) {
    is RegisterState.RegisterError -> error
    is RegisterState.RegisterNoConnection -> errorMessage
    is RegisterState.RegisterTimeoutError -> message
    is RegisterState.RegisterUnauthorized -> message
    is RegisterState.RegisterValidationError -> message
    is RegisterState.RegisterUnknown -> message
    else -> ""
}

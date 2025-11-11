package com.fabianospdev.volunteerscompose.features.login.presentation.states

import com.fabianospdev.volunteerscompose.core.domain.models.ErrorType
import com.fabianospdev.volunteerscompose.features.login.domain.entities.LoginResponseEntity

sealed class LoginState {
    object LoginLoading : LoginState()
    object LoginIdle : LoginState()
    data class LoginSuccess(val response: LoginResponseEntity) : LoginState()
    data class LoginError(val error: String) : LoginState()
    data class LoginNoConnection(val message: String) : LoginState()
    data class LoginValidationError(val message: String) : LoginState()
    data class LoginTimeoutError(val message: String) : LoginState()
    data class LoginUnauthorized(val message: String) : LoginState()
    data class LoginUnknown(val message: String) : LoginState() {
        override fun toString(): String {
            return "Error unknown occurred with message: $message"
        }
    }
}

/** Função auxiliar para mapear o tipo de erro */
fun LoginState.toErrorType(): ErrorType = when (this) {
    is LoginState.LoginNoConnection -> ErrorType.NETWORK
    is LoginState.LoginTimeoutError -> ErrorType.TIMEOUT
    is LoginState.LoginUnauthorized -> ErrorType.UNAUTHORIZED
    is LoginState.LoginValidationError -> ErrorType.VALIDATION
    else -> ErrorType.UNKNOWN
}
package com.fabianospdev.volunteerscompose.features.login.presentation

import com.fabianospdev.volunteerscompose.core.domain.models.ErrorType
import com.fabianospdev.volunteerscompose.features.login.domain.entities.LoginResponseEntity
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginState

open class FakeLoginState {
    object LoginLoading : FakeLoginState()
    object LoginIdle : FakeLoginState()
    data class LoginSuccess(val response: LoginResponseEntity) : FakeLoginState()
    data class LoginError(val error: String) : FakeLoginState()
    data class LoginNoConnection(val message: String) : FakeLoginState()
    data class LoginValidationError(val message: String) : FakeLoginState()
    data class LoginTimeoutError(val message: String) : FakeLoginState()
    data class LoginUnauthorized(val message: String) : FakeLoginState()
    data class LoginUnknown(val message: String) : FakeLoginState() {
        override fun toString(): String {
            return "Error unknown occurred with message: $message"
        }
    }
}

/** Função auxiliar para mapear o tipo de erro */
fun FakeLoginState.toErrorType(): ErrorType = when (this) {
    is FakeLoginState.LoginNoConnection -> ErrorType.NETWORK
    is FakeLoginState.LoginTimeoutError -> ErrorType.TIMEOUT
    is FakeLoginState.LoginUnauthorized -> ErrorType.UNAUTHORIZED
    is FakeLoginState.LoginValidationError -> ErrorType.VALIDATION
    else -> ErrorType.UNKNOWN
}
package com.fabianospdev.baseapp.features.home.presentation

import com.fabianospdev.baseapp.core.domain.models.ErrorType
import com.fabianospdev.baseapp.features.home.domain.entities.HomeEntity

sealed class HomeState {
    object HomeLoading : HomeState()

    object HomeIdle : HomeState()

    data class HomeSuccess(val response: HomeEntity) : HomeState()

    data class HomeError(val error: String) : HomeState()
    data class HomeNoConnection(val message: String) : HomeState()

    data class HomeValidationError(val message: String) : HomeState()

    data class HomeTimeoutError(val message: String) : HomeState()

    data class HomeUnauthorized(val message: String) : HomeState()

    data class HomeUnknown(val message: String) : HomeState() {
        override fun toString(): String {
            return "Error unknown occurred with message: $message"
        }
    }
}

/** Função auxiliar para mapear o tipo de erro */
fun HomeState.toErrorType(): ErrorType = when (this) {
    is HomeState.HomeNoConnection -> ErrorType.NETWORK
    is HomeState.HomeTimeoutError -> ErrorType.TIMEOUT
    is HomeState.HomeUnauthorized -> ErrorType.UNAUTHORIZED
    is HomeState.HomeValidationError -> ErrorType.VALIDATION
    else -> ErrorType.UNKNOWN
}
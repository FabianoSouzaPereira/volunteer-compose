package com.fabianospdev.volunteerscompose.features.profile.presentation.states

import com.fabianospdev.volunteerscompose.core.domain.models.ErrorType
import com.fabianospdev.volunteerscompose.features.profile.domain.entities.ProfileResponseEntity

sealed class ProfileState {
    object ProfileLoading : ProfileState()
    object ProfileIdle : ProfileState()
    data class ProfileSuccess(val response: ProfileResponseEntity) : ProfileState()
    data class ProfileError(val error: String) : ProfileState()
    data class ProfileNoConnection(val errorMessage: String) : ProfileState()
    data class ProfileValidationError(val message: String) : ProfileState()
    data class ProfileTimeoutError(val message: String) : ProfileState()
    data class ProfileUnauthorized(val message: String) : ProfileState()
    data class ProfileUnknown(val message: String) : ProfileState()
}

fun ProfileState.toErrorType(): ErrorType = when (this) {
    is ProfileState.ProfileNoConnection -> ErrorType.NETWORK
    is ProfileState.ProfileTimeoutError -> ErrorType.TIMEOUT
    is ProfileState.ProfileUnauthorized -> ErrorType.UNAUTHORIZED
    is ProfileState.ProfileValidationError -> ErrorType.VALIDATION
    else -> ErrorType.UNKNOWN
}

fun ProfileState.errorText(): String = when (this) {
    is ProfileState.ProfileError -> error
    is ProfileState.ProfileNoConnection -> errorMessage
    is ProfileState.ProfileTimeoutError -> message
    is ProfileState.ProfileUnauthorized -> message
    is ProfileState.ProfileValidationError -> message
    is ProfileState.ProfileUnknown -> message
    else -> ""
}

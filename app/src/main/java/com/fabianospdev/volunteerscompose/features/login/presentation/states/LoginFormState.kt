package com.fabianospdev.volunteerscompose.features.login.presentation.states

data class LoginFormState(
    val username: String = "",
    val password: String = "",
    val usernameError: String? = null,
    val passwordError: String? = null,
    val isFormValid: Boolean = false,
    val showPassword: Boolean = false
)


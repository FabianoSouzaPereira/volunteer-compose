package com.fabianospdev.volunteerscompose.features.login.presentation.states

data class LoginActions(
    val onLoginClick: () -> Unit = {},
    val onUsernameChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val onTogglePasswordVisibility: () -> Unit = {},
    val onRetry: () -> Unit = {},
    val onClearInputFields: () -> Unit = {},
    val onNavigateToForgotPassword: () -> Unit = {},
    val onNavigateToRegister: () -> Unit = {},
    val onNavigateToSettings: () -> Unit = {}
)

package com.fabianospdev.volunteerscompose.features.register.presentation.states

data class RegisterActions(
    val onNameChange: (String) -> Unit = {},
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val onConfirmPasswordChange: (String) -> Unit = {},
    val onTogglePasswordVisibility: () -> Unit = {},
    val onSubmit: () -> Unit = {},
    val onRetry: () -> Unit = {},
    val onNavigateBack: () -> Unit = {},
    val onNavigateToLogin: () -> Unit = {}
)

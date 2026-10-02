package com.fabianospdev.volunteerscompose.features.forgotpassword.presentation.states

data class ForgotPasswordActions(
    val onEmailChange: (String) -> Unit = {},
    val onSubmit: () -> Unit = {},
    val onRetry: () -> Unit = {},
    val onNavigateBack: () -> Unit = {},
    val onNavigateToLogin: () -> Unit = {}
)

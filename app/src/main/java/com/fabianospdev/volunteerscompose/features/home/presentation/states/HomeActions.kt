package com.fabianospdev.volunteerscompose.features.home.presentation.states

data class HomeActions(
    val onNavigateToSettings: () -> Unit = {},
    val onNavigateToProfile: () -> Unit = {},
    val onNavigateToLogin: () -> Unit = {},
    val onRetry: () -> Unit = {}
)

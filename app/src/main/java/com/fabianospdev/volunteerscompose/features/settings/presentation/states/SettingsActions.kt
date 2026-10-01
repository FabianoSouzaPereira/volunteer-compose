package com.fabianospdev.volunteerscompose.features.settings.presentation.states

data class SettingsActions(
    val onDarkModeToggle: (Boolean) -> Unit = {},
    val onNotificationsToggle: (Boolean) -> Unit = {},
    val onNavigateBack: () -> Unit = {},
    val onNavigateToProfile: () -> Unit = {},
    val onNavigateToAbout: () -> Unit = {},
    val onLogout: () -> Unit = {},
    val onRetry: () -> Unit = {}
)

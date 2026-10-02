package com.fabianospdev.volunteerscompose.features.profile.presentation.states

data class ProfileActions(
    val onNameChange: (String) -> Unit = {},
    val onPhoneChange: (String) -> Unit = {},
    val onSave: () -> Unit = {},
    val onNavigateBack: () -> Unit = {},
    val onRetry: () -> Unit = {}
)

package com.fabianospdev.volunteerscompose.features.settings.presentation.states

import com.fabianospdev.volunteerscompose.features.settings.domain.entities.SettingsResponseEntity

data class SettingsViewState(
    val screenState: SettingsState = SettingsState.SettingsIdle,
    val settings: SettingsResponseEntity = SettingsResponseEntity(
        darkMode = false,
        notifications = true,
        language = "Português",
        theme = "Default"
    )
)

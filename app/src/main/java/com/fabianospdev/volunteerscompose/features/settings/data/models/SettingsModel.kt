package com.fabianospdev.volunteerscompose.features.settings.data.models

import com.fabianospdev.volunteerscompose.features.settings.domain.entities.SettingsResponseEntity

data class SettingsModel(
    val darkMode: Boolean,
    val notifications: Boolean,
    val language: String,
    val theme: String
)

fun SettingsModel.toEntity(): SettingsResponseEntity {
    return SettingsResponseEntity(
        darkMode = darkMode,
        notifications = notifications,
        language = language,
        theme = theme
    )
}

fun SettingsResponseEntity.toModel(): SettingsModel {
    return SettingsModel(
        darkMode = darkMode,
        notifications = notifications,
        language = language,
        theme = theme
    )
}

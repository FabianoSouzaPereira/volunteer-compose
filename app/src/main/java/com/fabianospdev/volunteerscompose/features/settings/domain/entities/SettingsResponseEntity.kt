package com.fabianospdev.volunteerscompose.features.settings.domain.entities

data class SettingsResponseEntity(
    val darkMode: Boolean,
    val notifications: Boolean,
    val language: String,
    val theme: String
)
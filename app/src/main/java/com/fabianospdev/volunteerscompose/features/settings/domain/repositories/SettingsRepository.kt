package com.fabianospdev.volunteerscompose.features.settings.domain.repositories

import com.fabianospdev.volunteerscompose.features.settings.domain.entities.SettingsResponseEntity

interface SettingsRepository {
    suspend fun getSettings(): Result<SettingsResponseEntity>
    suspend fun saveSettings(settings: SettingsResponseEntity): Result<Unit>
}

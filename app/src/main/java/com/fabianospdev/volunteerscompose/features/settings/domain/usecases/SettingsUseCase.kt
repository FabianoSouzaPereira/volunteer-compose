package com.fabianospdev.volunteerscompose.features.settings.domain.usecases

import com.fabianospdev.volunteerscompose.features.settings.domain.entities.SettingsResponseEntity

interface SettingsUseCase {
    suspend fun getSettings(): Result<SettingsResponseEntity>
    suspend fun saveSettings(settings: SettingsResponseEntity): Result<Unit>
}

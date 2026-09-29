package com.fabianospdev.volunteerscompose.features.settings.data.datasources

import com.fabianospdev.volunteerscompose.features.settings.data.models.SettingsModel

interface SettingsDatasource {
    suspend fun getSettings(): Result<SettingsModel>
    suspend fun saveSettings(settings: SettingsModel): Result<Unit>
}

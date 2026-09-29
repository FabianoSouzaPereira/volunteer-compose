package com.fabianospdev.volunteerscompose.features.settings.data.datasources

import com.fabianospdev.volunteerscompose.core.helpers.exceptions.toRequestException
import com.fabianospdev.volunteerscompose.features.settings.data.models.SettingsModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsDatasourceImpl @Inject constructor() : SettingsDatasource {

    @Volatile
    private var current = SettingsModel(
        darkMode = false,
        notifications = true,
        language = "Português",
        theme = "Default"
    )

    override suspend fun getSettings(): Result<SettingsModel> {
        return try {
            Result.success(current)
        } catch (e: Exception) {
            Result.failure(e.toRequestException())
        }
    }

    override suspend fun saveSettings(settings: SettingsModel): Result<Unit> {
        return try {
            current = settings
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e.toRequestException())
        }
    }
}

package com.fabianospdev.volunteerscompose.features.settings.data.repositories

import com.fabianospdev.volunteerscompose.features.settings.data.datasources.SettingsDatasource
import com.fabianospdev.volunteerscompose.features.settings.data.models.toEntity
import com.fabianospdev.volunteerscompose.features.settings.data.models.toModel
import com.fabianospdev.volunteerscompose.features.settings.domain.entities.SettingsResponseEntity
import com.fabianospdev.volunteerscompose.features.settings.domain.repositories.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDatasource: SettingsDatasource
) : SettingsRepository {
    override suspend fun getSettings(): Result<SettingsResponseEntity> {
        return settingsDatasource.getSettings().map { it.toEntity() }
    }

    override suspend fun saveSettings(settings: SettingsResponseEntity): Result<Unit> {
        return settingsDatasource.saveSettings(settings.toModel())
    }
}

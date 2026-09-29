package com.fabianospdev.volunteerscompose.features.settings.domain.usecases

import com.fabianospdev.volunteerscompose.features.settings.domain.entities.SettingsResponseEntity
import com.fabianospdev.volunteerscompose.features.settings.domain.repositories.SettingsRepository
import javax.inject.Inject

class SettingsUseCaseImpl @Inject constructor(
    private val repository: SettingsRepository
) : SettingsUseCase {
    override suspend fun getSettings(): Result<SettingsResponseEntity> {
        return repository.getSettings()
    }

    override suspend fun saveSettings(settings: SettingsResponseEntity): Result<Unit> {
        return repository.saveSettings(settings)
    }
}

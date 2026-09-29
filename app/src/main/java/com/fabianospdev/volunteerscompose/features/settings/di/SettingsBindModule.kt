package com.fabianospdev.volunteerscompose.features.settings.di

import com.fabianospdev.volunteerscompose.features.settings.data.datasources.SettingsDatasource
import com.fabianospdev.volunteerscompose.features.settings.data.datasources.SettingsDatasourceImpl
import com.fabianospdev.volunteerscompose.features.settings.data.repositories.SettingsRepositoryImpl
import com.fabianospdev.volunteerscompose.features.settings.domain.repositories.SettingsRepository
import com.fabianospdev.volunteerscompose.features.settings.domain.usecases.SettingsUseCase
import com.fabianospdev.volunteerscompose.features.settings.domain.usecases.SettingsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsBindModule {

    @Binds
    @Singleton
    abstract fun bindSettingsDatasource(impl: SettingsDatasourceImpl): SettingsDatasource

    @Binds
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    abstract fun bindSettingsUseCase(impl: SettingsUseCaseImpl): SettingsUseCase
}

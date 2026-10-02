package com.fabianospdev.volunteerscompose.features.profile.di

import com.fabianospdev.volunteerscompose.features.profile.data.datasources.ProfileDatasource
import com.fabianospdev.volunteerscompose.features.profile.data.datasources.ProfileDatasourceImpl
import com.fabianospdev.volunteerscompose.features.profile.data.repositories.ProfileRepositoryImpl
import com.fabianospdev.volunteerscompose.features.profile.domain.repositories.ProfileRepository
import com.fabianospdev.volunteerscompose.features.profile.domain.usecases.ProfileUseCase
import com.fabianospdev.volunteerscompose.features.profile.domain.usecases.ProfileUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileBindModule {

    @Binds
    @Singleton
    abstract fun bindProfileDatasource(impl: ProfileDatasourceImpl): ProfileDatasource

    @Binds
    abstract fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    abstract fun bindProfileUseCase(impl: ProfileUseCaseImpl): ProfileUseCase
}

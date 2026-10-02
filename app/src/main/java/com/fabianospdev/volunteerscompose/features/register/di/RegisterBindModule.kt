package com.fabianospdev.volunteerscompose.features.register.di

import com.fabianospdev.volunteerscompose.features.register.data.datasources.RegisterDatasource
import com.fabianospdev.volunteerscompose.features.register.data.datasources.RegisterDatasourceImpl
import com.fabianospdev.volunteerscompose.features.register.data.repositories.RegisterRepositoryImpl
import com.fabianospdev.volunteerscompose.features.register.domain.repositories.RegisterRepository
import com.fabianospdev.volunteerscompose.features.register.domain.usecases.RegisterUseCase
import com.fabianospdev.volunteerscompose.features.register.domain.usecases.RegisterUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RegisterBindModule {

    @Binds
    @Singleton
    abstract fun bindRegisterDatasource(impl: RegisterDatasourceImpl): RegisterDatasource

    @Binds
    abstract fun bindRegisterRepository(impl: RegisterRepositoryImpl): RegisterRepository

    @Binds
    abstract fun bindRegisterUseCase(impl: RegisterUseCaseImpl): RegisterUseCase
}

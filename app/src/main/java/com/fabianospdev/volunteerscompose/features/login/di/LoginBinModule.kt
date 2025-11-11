package com.fabianospdev.volunteerscompose.features.login.di

import com.fabianospdev.volunteerscompose.features.login.data.datasources.LoginDatasourceImpl
import com.fabianospdev.volunteerscompose.features.login.data.repositories.LoginRepositoryImpl
import com.fabianospdev.volunteerscompose.features.login.domain.datasources.LoginDatasource
import com.fabianospdev.volunteerscompose.features.login.domain.repositories.LoginRepository
import com.fabianospdev.volunteerscompose.features.login.domain.usecases.LoginUsecase
import com.fabianospdev.volunteerscompose.features.login.domain.usecases.LoginUsecaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginBinModule {

    @Binds
    abstract fun bindLoginDatasource(
        impl: LoginDatasourceImpl
    ): LoginDatasource

    @Binds
    abstract fun bindLoginRepository(
        impl: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    abstract fun bindLoginUsecase(
        impl: LoginUsecaseImpl
    ): LoginUsecase
}
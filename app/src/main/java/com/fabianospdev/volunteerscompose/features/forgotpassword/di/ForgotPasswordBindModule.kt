package com.fabianospdev.volunteerscompose.features.forgotpassword.di

import com.fabianospdev.volunteerscompose.features.forgotpassword.data.datasources.ForgotPasswordDatasource
import com.fabianospdev.volunteerscompose.features.forgotpassword.data.datasources.ForgotPasswordDatasourceImpl
import com.fabianospdev.volunteerscompose.features.forgotpassword.data.repositories.ForgotPasswordRepositoryImpl
import com.fabianospdev.volunteerscompose.features.forgotpassword.domain.repositories.ForgotPasswordRepository
import com.fabianospdev.volunteerscompose.features.forgotpassword.domain.usecases.ForgotPasswordUseCase
import com.fabianospdev.volunteerscompose.features.forgotpassword.domain.usecases.ForgotPasswordUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ForgotPasswordBindModule {

    @Binds
    abstract fun bindForgotPasswordDatasource(
        impl: ForgotPasswordDatasourceImpl
    ): ForgotPasswordDatasource

    @Binds
    abstract fun bindForgotPasswordRepository(
        impl: ForgotPasswordRepositoryImpl
    ): ForgotPasswordRepository

    @Binds
    abstract fun bindForgotPasswordUseCase(
        impl: ForgotPasswordUseCaseImpl
    ): ForgotPasswordUseCase
}

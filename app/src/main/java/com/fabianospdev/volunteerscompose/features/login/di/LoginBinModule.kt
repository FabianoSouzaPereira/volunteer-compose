package com.fabianospdev.volunteerscompose.features.login.di

import com.fabianospdev.volunteerscompose.features.login.data.datasources.LoginDatasourceImpl
import com.fabianospdev.volunteerscompose.features.login.data.repositories.LoginRepositoryImpl
import com.fabianospdev.volunteerscompose.features.login.domain.datasources.LoginDatasource
import com.fabianospdev.volunteerscompose.features.login.domain.repositories.LoginRepository
import com.fabianospdev.volunteerscompose.features.login.domain.usecases.AuthUseCases
import com.fabianospdev.volunteerscompose.features.login.domain.usecases.AuthUseCasesImpl
import com.fabianospdev.volunteerscompose.features.login.domain.usecases.CloudFunctionsUseCases
import com.fabianospdev.volunteerscompose.features.login.domain.usecases.CloudFunctionsUseCasesImpl
import com.fabianospdev.volunteerscompose.features.login.domain.usecases.FirestoreUseCases
import com.fabianospdev.volunteerscompose.features.login.domain.usecases.FirestoreUseCasesImpl
import com.fabianospdev.volunteerscompose.features.login.domain.usecases.LoginUsecase
import com.fabianospdev.volunteerscompose.features.login.domain.usecases.LoginUsecaseImpl
import com.fabianospdev.volunteerscompose.features.login.domain.usecases.StorageUseCases
import com.fabianospdev.volunteerscompose.features.login.domain.usecases.StorageUseCasesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginBinModule {

    @Binds
    abstract fun bindLoginDatasource(impl: LoginDatasourceImpl): LoginDatasource

    @Binds
    abstract fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun bindLoginUsecase(impl: LoginUsecaseImpl): LoginUsecase


    @Binds
    @Singleton
    abstract fun bindAuthUseCases(impl: AuthUseCasesImpl): AuthUseCases

    @Binds
    @Singleton
    abstract fun bindFirestoreUseCases(impl: FirestoreUseCasesImpl): FirestoreUseCases

    @Binds
    @Singleton
    abstract fun bindStorageUseCases(impl: StorageUseCasesImpl): StorageUseCases

    @Binds
    @Singleton
    abstract fun bindCloudFunctionsUseCases(impl: CloudFunctionsUseCasesImpl): CloudFunctionsUseCases
}
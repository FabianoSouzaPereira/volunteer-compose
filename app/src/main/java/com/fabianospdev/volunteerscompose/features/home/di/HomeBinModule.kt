package com.fabianospdev.volunteerscompose.features.home.di

import com.fabianospdev.volunteerscompose.features.home.data.datasources.HomeDatasourceImpl
import com.fabianospdev.volunteerscompose.features.home.data.repositories.HomeRepositoryImpl
import com.fabianospdev.volunteerscompose.features.home.domain.datasources.HomeDatasource
import com.fabianospdev.volunteerscompose.features.home.domain.repositories.HomeRepository
import com.fabianospdev.volunteerscompose.features.home.domain.usecases.HomeUseCase
import com.fabianospdev.volunteerscompose.features.home.domain.usecases.HomeUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeBinModule {

    @Binds
    abstract fun bindHomeDatasouce(impl: HomeDatasourceImpl): HomeDatasource

    @Binds
    abstract fun bindHomeRepository(impl: HomeRepositoryImpl): HomeRepository

    @Binds
    abstract fun bindHomeUsecase(impl: HomeUseCaseImpl): HomeUseCase

}
package com.fabianospdev.baseapp.features.home.di

import com.fabianospdev.baseapp.features.home.domain.repositories.HomeRepository
import com.fabianospdev.baseapp.features.home.domain.usecases.HomeUseCase
import com.fabianospdev.baseapp.features.home.domain.usecases.HomeUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeBinModule {

    @Binds
    abstract fun bindHomeDatasouce(impl: HomeUseCaseImpl): HomeUseCase

    @Binds
    abstract fun bindHomeRepository(impl: HomeUseCaseImpl): HomeRepository

    @Binds
    abstract fun bindHomeUsecase(impl: HomeUseCaseImpl): HomeUseCase

}
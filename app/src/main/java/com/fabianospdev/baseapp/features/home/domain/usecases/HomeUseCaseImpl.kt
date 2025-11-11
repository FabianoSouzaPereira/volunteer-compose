package com.fabianospdev.baseapp.features.home.domain.usecases

import com.fabianospdev.baseapp.features.home.domain.entities.HomeEntity
import com.fabianospdev.baseapp.features.home.domain.repositories.HomeRepository
import javax.inject.Inject

class HomeUseCaseImpl @Inject constructor(private val repository: HomeRepository) : HomeUseCase {
    override suspend fun getHomeData(): Result<HomeEntity> {
        return repository.getHomeData()
    }
}
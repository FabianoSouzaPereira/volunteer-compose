package com.fabianospdev.baseapp.features.home.domain.usecases

import com.fabianospdev.baseapp.features.home.domain.entities.HomeEntity

interface HomeUseCase {
    suspend fun getHomeData(): Result<HomeEntity>
}
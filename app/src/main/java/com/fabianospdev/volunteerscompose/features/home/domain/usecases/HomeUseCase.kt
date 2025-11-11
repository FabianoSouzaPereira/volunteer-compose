package com.fabianospdev.volunteerscompose.features.home.domain.usecases

import com.fabianospdev.volunteerscompose.features.home.domain.entities.HomeEntity

interface HomeUseCase {
    suspend fun getHomeData(): Result<HomeEntity>
}
package com.fabianospdev.volunteerscompose.features.home.domain.repositories

import com.fabianospdev.volunteerscompose.features.home.domain.entities.HomeEntity

interface HomeRepository {
    suspend fun getHomeData(): Result<HomeEntity>
}
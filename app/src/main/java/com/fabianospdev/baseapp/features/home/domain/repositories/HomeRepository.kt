package com.fabianospdev.baseapp.features.home.domain.repositories

import com.fabianospdev.baseapp.features.home.domain.entities.HomeEntity

interface HomeRepository {
    suspend fun getHomeData(): Result<HomeEntity>
}
package com.fabianospdev.baseapp.features.home.domain.datasources

import com.fabianospdev.baseapp.features.home.data.models.HomeModel

interface HomeDatasource {
    suspend fun getHomeData(): Result<HomeModel>
}
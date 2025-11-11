package com.fabianospdev.volunteerscompose.features.home.domain.datasources

import com.fabianospdev.volunteerscompose.features.home.data.models.HomeModel

interface HomeDatasource {
    suspend fun getHomeData(): Result<HomeModel>
}
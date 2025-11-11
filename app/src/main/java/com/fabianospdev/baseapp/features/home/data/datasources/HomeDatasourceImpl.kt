package com.fabianospdev.baseapp.features.home.data.datasources

import com.fabianospdev.baseapp.features.home.data.models.HomeModel
import com.fabianospdev.baseapp.features.home.data.remote.HomeAPIService
import com.fabianospdev.baseapp.features.home.domain.datasources.HomeDatasource
import jakarta.inject.Inject

class HomeDatasourceImpl @Inject constructor(
    private val homeApiService: HomeAPIService,
) : HomeDatasource {
    override suspend fun getHomeData(): Result<HomeModel> {
        return homeApiService.getHomeData()
    }
}
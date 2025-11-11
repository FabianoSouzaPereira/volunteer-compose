package com.fabianospdev.volunteerscompose.features.home.data.datasources

import com.fabianospdev.volunteerscompose.features.home.data.models.HomeModel
import com.fabianospdev.volunteerscompose.features.home.data.remote.HomeApiService
import com.fabianospdev.volunteerscompose.features.home.domain.datasources.HomeDatasource
import jakarta.inject.Inject

class HomeDatasourceImpl @Inject constructor(
    private val homeApiService: HomeApiService,
) : HomeDatasource {
    override suspend fun getHomeData(): Result<HomeModel> {
        return homeApiService.getHomeData()
    }
}
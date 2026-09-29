package com.fabianospdev.volunteerscompose.features.home.data.repositories

import com.fabianospdev.volunteerscompose.features.home.data.datasources.HomeDatasource
import com.fabianospdev.volunteerscompose.features.home.data.models.toEntity
import com.fabianospdev.volunteerscompose.features.home.domain.entities.HomeEntity
import com.fabianospdev.volunteerscompose.features.home.domain.repositories.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDatasource: HomeDatasource
) : HomeRepository {
    override suspend fun getHomeData(): Result<HomeEntity> {
        return homeDatasource.getHomeData().map { it.toEntity() }
    }
}

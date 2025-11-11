package com.fabianospdev.baseapp.features.home.data.repositories

import com.fabianospdev.baseapp.features.home.data.models.toEntity
import com.fabianospdev.baseapp.features.home.domain.datasources.HomeDatasource
import com.fabianospdev.baseapp.features.home.domain.entities.HomeEntity
import com.fabianospdev.baseapp.features.home.domain.repositories.HomeRepository
import javax.inject.Inject

class HomeRepositoryFunction @Inject constructor(private val homeDatasource: HomeDatasource) :
    HomeRepository {
    override suspend fun getHomeData(): Result<HomeEntity> {
        return try {
            val responseModel = homeDatasource.getHomeData().getOrThrow()
            Result.success(responseModel.toEntity())
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}
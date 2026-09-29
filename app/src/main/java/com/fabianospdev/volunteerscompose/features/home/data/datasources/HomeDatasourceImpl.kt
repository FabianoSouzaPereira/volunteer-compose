package com.fabianospdev.volunteerscompose.features.home.data.datasources

import com.fabianospdev.volunteerscompose.core.helpers.exceptions.toRequestException
import com.fabianospdev.volunteerscompose.features.home.data.models.HomeModel
import javax.inject.Inject

class HomeDatasourceImpl @Inject constructor() : HomeDatasource {
    override suspend fun getHomeData(): Result<HomeModel> {
        return try {
            // Placeholder until the real Home API is wired through HomeApiService.
            Result.success(
                HomeModel(
                    welcomeMessage = "Sample Home Data",
                    title = "Home",
                    description = "Additional Info"
                )
            )
        } catch (e: Exception) {
            Result.failure(e.toRequestException())
        }
    }
}

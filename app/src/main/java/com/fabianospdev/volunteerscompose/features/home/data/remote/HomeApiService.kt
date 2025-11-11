package com.fabianospdev.volunteerscompose.features.home.data.remote

import com.fabianospdev.volunteerscompose.features.home.data.models.HomeModel
import retrofit2.http.GET

interface HomeApiService {
    @GET("home")
    suspend fun getHomeData(): Result<HomeModel>
}
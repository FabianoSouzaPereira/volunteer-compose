package com.fabianospdev.baseapp.features.home.data.remote

import com.fabianospdev.baseapp.features.home.data.models.HomeModel
import retrofit2.http.GET

interface HomeAPIService {

    @GET("home")
    suspend fun getHomeData(): Result<HomeModel>
}
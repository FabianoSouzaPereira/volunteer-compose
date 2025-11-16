package com.fabianospdev.volunteerscompose.features.login.data.remote

import com.fabianospdev.volunteerscompose.features.login.data.models.LoginResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginApiService {

    @GET("login")
    suspend fun getLogin(
        @Query("email") email: String,
        @Query("password") password: String
    ): LoginResponseModel
}
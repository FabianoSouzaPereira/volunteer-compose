package com.fabianospdev.volunteerscompose.features.login.data.remote

import com.fabianospdev.volunteerscompose.features.login.data.models.LoginResponseModel
import retrofit2.http.GET

interface LoginApiService {
    @GET("login")
    suspend fun getLogin(email: String, password: String): LoginResponseModel
}
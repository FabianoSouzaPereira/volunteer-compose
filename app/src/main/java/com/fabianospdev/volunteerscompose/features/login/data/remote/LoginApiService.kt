package com.fabianospdev.volunteerscompose.features.login.data.remote

import com.fabianospdev.volunteerscompose.features.login.data.models.LoginRequestModel
import com.fabianospdev.volunteerscompose.features.login.data.models.LoginResponseModel
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {

    @POST("login")
    suspend fun login(@Body request: LoginRequestModel): LoginResponseModel
}

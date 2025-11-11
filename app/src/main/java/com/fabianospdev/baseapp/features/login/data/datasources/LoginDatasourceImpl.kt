package com.fabianospdev.baseapp.features.login.data.datasources

import com.fabianospdev.baseapp.features.login.data.models.LoginResponseModel
import com.fabianospdev.baseapp.features.login.data.remote.LoginApiService
import com.fabianospdev.baseapp.features.login.domain.datasources.LoginDatasource
import javax.inject.Inject

class LoginDatasourceImpl @Inject constructor(
    private val api: LoginApiService
) : LoginDatasource {

    override suspend fun getLogin(email: String, password: String): Result<LoginResponseModel> {
        return try {
            val response = api.getLogin(email, password)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(Throwable("Authentication error: ${e.message}", e))
        }
    }
}
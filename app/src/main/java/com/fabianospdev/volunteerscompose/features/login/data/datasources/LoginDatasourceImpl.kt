package com.fabianospdev.volunteerscompose.features.login.data.datasources

import com.fabianospdev.volunteerscompose.core.helpers.exceptions.toRequestException
import com.fabianospdev.volunteerscompose.features.login.data.models.LoginRequestModel
import com.fabianospdev.volunteerscompose.features.login.data.models.LoginResponseModel
import com.fabianospdev.volunteerscompose.features.login.data.remote.LoginApiService
import javax.inject.Inject

class LoginDatasourceImpl @Inject constructor(
    private val api: LoginApiService
) : LoginDatasource {
    override suspend fun getLogin(email: String, password: String): Result<LoginResponseModel> {
        return Result.success(LoginResponseModel(id = "1", name = "Fabiano",email = "email@email.com" ,token = "token"))
        return try {
            val response = api.login(LoginRequestModel(email = email, password = password))
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e.toRequestException())
        }
    }
}

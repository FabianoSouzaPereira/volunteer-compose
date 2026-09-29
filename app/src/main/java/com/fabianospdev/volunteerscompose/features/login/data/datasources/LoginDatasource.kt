package com.fabianospdev.volunteerscompose.features.login.data.datasources

import com.fabianospdev.volunteerscompose.features.login.data.models.LoginResponseModel

interface LoginDatasource {
    suspend fun getLogin(email: String, password: String): Result<LoginResponseModel>
}

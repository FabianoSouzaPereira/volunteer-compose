package com.fabianospdev.baseapp.features.login.domain.datasources

import com.fabianospdev.baseapp.features.login.data.models.LoginResponseModel

interface LoginDatasource {
    suspend fun getLogin(email: String, password: String): Result<LoginResponseModel>
}
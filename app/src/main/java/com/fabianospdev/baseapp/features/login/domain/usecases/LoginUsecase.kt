package com.fabianospdev.baseapp.features.login.domain.usecases

import com.fabianospdev.baseapp.features.login.domain.entities.LoginResponseEntity

interface LoginUsecase {
    suspend fun getLogin(email: String, password: String): Result<LoginResponseEntity>
}
package com.fabianospdev.volunteerscompose.features.login.domain.usecases

import com.fabianospdev.volunteerscompose.features.login.domain.entities.LoginResponseEntity

interface LoginUsecase {
    suspend fun getLogin(email: String, password: String): Result<LoginResponseEntity>
}
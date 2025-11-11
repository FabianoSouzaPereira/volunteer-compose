package com.fabianospdev.volunteerscompose.features.login.domain.repositories

import com.fabianospdev.volunteerscompose.features.login.domain.entities.LoginResponseEntity

interface LoginRepository {
    suspend fun getLogin(email: String, password: String): Result<LoginResponseEntity>
}
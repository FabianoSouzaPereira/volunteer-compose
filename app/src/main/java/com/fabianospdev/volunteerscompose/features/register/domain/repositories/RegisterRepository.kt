package com.fabianospdev.volunteerscompose.features.register.domain.repositories

import com.fabianospdev.volunteerscompose.features.register.domain.entities.RegisterResponseEntity

interface RegisterRepository {
    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<RegisterResponseEntity>
}

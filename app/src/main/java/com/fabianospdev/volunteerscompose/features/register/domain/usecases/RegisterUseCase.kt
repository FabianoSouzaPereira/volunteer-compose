package com.fabianospdev.volunteerscompose.features.register.domain.usecases

import com.fabianospdev.volunteerscompose.features.register.domain.entities.RegisterResponseEntity

interface RegisterUseCase {
    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<RegisterResponseEntity>
}

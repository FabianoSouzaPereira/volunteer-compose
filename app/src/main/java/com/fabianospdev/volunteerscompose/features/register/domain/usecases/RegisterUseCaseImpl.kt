package com.fabianospdev.volunteerscompose.features.register.domain.usecases

import com.fabianospdev.volunteerscompose.features.register.domain.entities.RegisterResponseEntity
import com.fabianospdev.volunteerscompose.features.register.domain.repositories.RegisterRepository
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(
    private val repository: RegisterRepository
) : RegisterUseCase {
    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<RegisterResponseEntity> {
        return repository.register(name, email, password)
    }
}

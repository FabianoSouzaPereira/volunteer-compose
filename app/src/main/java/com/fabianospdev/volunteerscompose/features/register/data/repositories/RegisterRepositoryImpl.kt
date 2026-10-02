package com.fabianospdev.volunteerscompose.features.register.data.repositories

import com.fabianospdev.volunteerscompose.features.register.data.datasources.RegisterDatasource
import com.fabianospdev.volunteerscompose.features.register.data.models.toEntity
import com.fabianospdev.volunteerscompose.features.register.domain.entities.RegisterResponseEntity
import com.fabianospdev.volunteerscompose.features.register.domain.repositories.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerDatasource: RegisterDatasource
) : RegisterRepository {
    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<RegisterResponseEntity> {
        return registerDatasource.register(name, email, password).map { it.toEntity() }
    }
}

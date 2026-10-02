package com.fabianospdev.volunteerscompose.features.register.data.datasources

import com.fabianospdev.volunteerscompose.features.register.data.models.RegisterResponseModel

interface RegisterDatasource {
    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<RegisterResponseModel>
}

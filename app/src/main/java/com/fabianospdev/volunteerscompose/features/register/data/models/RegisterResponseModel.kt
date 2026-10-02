package com.fabianospdev.volunteerscompose.features.register.data.models

import com.fabianospdev.volunteerscompose.features.register.domain.entities.RegisterResponseEntity

data class RegisterResponseModel(
    val name: String,
    val email: String,
    val message: String
)

fun RegisterResponseModel.toEntity(): RegisterResponseEntity {
    return RegisterResponseEntity(
        name = name,
        email = email,
        message = message
    )
}

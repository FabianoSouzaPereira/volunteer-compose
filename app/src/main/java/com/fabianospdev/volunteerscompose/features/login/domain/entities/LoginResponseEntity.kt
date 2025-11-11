package com.fabianospdev.volunteerscompose.features.login.domain.entities

data class LoginResponseEntity(
    val id: String,
    val name: String,
    val email: String,
    val token: String
)
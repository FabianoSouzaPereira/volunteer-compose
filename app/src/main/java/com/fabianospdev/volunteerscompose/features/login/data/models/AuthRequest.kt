package com.fabianospdev.volunteerscompose.features.login.data.models

data class AuthRequest(
    val email: String,
    val password: String,
    val displayName: String? = null,
    val returnSecureToken: Boolean = true
)

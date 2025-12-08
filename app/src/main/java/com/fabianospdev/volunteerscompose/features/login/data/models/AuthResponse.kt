package com.fabianospdev.volunteerscompose.features.login.data.models

data class AuthResponse(
    val idToken: String,
    val email: String,
    val refreshToken: String,
    val expiresIn: String,
    val localId: String,
    val displayName: String? = null,
    val photoUrl: String? = null
)

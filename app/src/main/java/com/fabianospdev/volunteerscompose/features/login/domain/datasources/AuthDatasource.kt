package com.fabianospdev.volunteerscompose.features.login.domain.datasources

import com.fabianospdev.volunteerscompose.features.login.data.models.AuthRequest
import com.fabianospdev.volunteerscompose.features.login.data.models.AuthResponse

interface AuthDatasource {
    suspend fun signUp(request: AuthRequest): Result<AuthResponse>
    suspend fun signIn(request: AuthRequest): Result<AuthResponse>
    suspend fun signInWithCustomToken(token: String): Result<AuthResponse>
    suspend fun refreshToken(refreshToken: String): Result<AuthResponse>
    suspend fun sendEmailVerification(email: String): Result<Unit>
    suspend fun sendPasswordReset(email: String): Result<Unit>
    suspend fun getAccountInfo(idToken: String): Result<AuthResponse>
    suspend fun deleteAccount(idToken: String): Result<Unit>
    suspend fun updateProfile(idToken: String, displayName: String?, photoUrl: String?): Result<AuthResponse>
}
package com.fabianospdev.volunteerscompose.features.login.domain.usecases

import com.fabianospdev.volunteerscompose.features.login.data.models.AuthRequest
import com.fabianospdev.volunteerscompose.features.login.data.models.AuthResponse
import com.fabianospdev.volunteerscompose.features.login.domain.repositories.AuthRepository
import jakarta.inject.Inject

class AuthUseCasesImpl @Inject constructor(
    private val authRepository: AuthRepository
) : AuthUseCases {

    override suspend fun signUp(request: AuthRequest): Result<AuthResponse> {
        return authRepository.signUp(request)
    }

    override suspend fun signIn(request: AuthRequest): Result<AuthResponse> {
        return authRepository.signIn(request)
    }

    override suspend fun signInWithCustomToken(token: String): Result<AuthResponse> {
        return authRepository.signInWithCustomToken(token)
    }

    override suspend fun refreshToken(refreshToken: String): Result<AuthResponse> {
        return authRepository.refreshToken(refreshToken)
    }

    override suspend fun sendEmailVerification(email: String): Result<Unit> {
        return authRepository.sendEmailVerification(email)
    }

    override suspend fun sendPasswordReset(email: String): Result<Unit> {
        return authRepository.sendPasswordReset(email)
    }

    override suspend fun getAccountInfo(idToken: String): Result<AuthResponse> {
        return authRepository.getAccountInfo(idToken)
    }

    override suspend fun deleteAccount(idToken: String): Result<Unit> {
        return authRepository.deleteAccount(idToken)
    }

    override suspend fun updateProfile(
        idToken: String,
        displayName: String?,
        photoUrl: String?
    ): Result<AuthResponse> {
        return authRepository.updateProfile(idToken, displayName, photoUrl)
    }
}

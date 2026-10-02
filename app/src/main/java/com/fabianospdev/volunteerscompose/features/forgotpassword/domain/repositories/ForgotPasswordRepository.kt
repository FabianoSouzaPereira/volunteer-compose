package com.fabianospdev.volunteerscompose.features.forgotpassword.domain.repositories

import com.fabianospdev.volunteerscompose.features.forgotpassword.domain.entities.ForgotPasswordResponseEntity

interface ForgotPasswordRepository {
    suspend fun requestPasswordReset(email: String): Result<ForgotPasswordResponseEntity>
}

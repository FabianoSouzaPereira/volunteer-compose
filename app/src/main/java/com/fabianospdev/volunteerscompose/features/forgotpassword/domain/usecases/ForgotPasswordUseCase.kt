package com.fabianospdev.volunteerscompose.features.forgotpassword.domain.usecases

import com.fabianospdev.volunteerscompose.features.forgotpassword.domain.entities.ForgotPasswordResponseEntity

interface ForgotPasswordUseCase {
    suspend fun requestPasswordReset(email: String): Result<ForgotPasswordResponseEntity>
}

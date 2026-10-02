package com.fabianospdev.volunteerscompose.features.forgotpassword.domain.usecases

import com.fabianospdev.volunteerscompose.features.forgotpassword.domain.entities.ForgotPasswordResponseEntity
import com.fabianospdev.volunteerscompose.features.forgotpassword.domain.repositories.ForgotPasswordRepository
import javax.inject.Inject

class ForgotPasswordUseCaseImpl @Inject constructor(
    private val repository: ForgotPasswordRepository
) : ForgotPasswordUseCase {
    override suspend fun requestPasswordReset(email: String): Result<ForgotPasswordResponseEntity> {
        return repository.requestPasswordReset(email)
    }
}

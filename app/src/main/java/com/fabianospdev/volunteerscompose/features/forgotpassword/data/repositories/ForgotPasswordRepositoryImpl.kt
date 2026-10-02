package com.fabianospdev.volunteerscompose.features.forgotpassword.data.repositories

import com.fabianospdev.volunteerscompose.features.forgotpassword.data.datasources.ForgotPasswordDatasource
import com.fabianospdev.volunteerscompose.features.forgotpassword.data.models.toEntity
import com.fabianospdev.volunteerscompose.features.forgotpassword.domain.entities.ForgotPasswordResponseEntity
import com.fabianospdev.volunteerscompose.features.forgotpassword.domain.repositories.ForgotPasswordRepository
import javax.inject.Inject

class ForgotPasswordRepositoryImpl @Inject constructor(
    private val forgotPasswordDatasource: ForgotPasswordDatasource
) : ForgotPasswordRepository {
    override suspend fun requestPasswordReset(email: String): Result<ForgotPasswordResponseEntity> {
        return forgotPasswordDatasource.requestPasswordReset(email).map { it.toEntity() }
    }
}

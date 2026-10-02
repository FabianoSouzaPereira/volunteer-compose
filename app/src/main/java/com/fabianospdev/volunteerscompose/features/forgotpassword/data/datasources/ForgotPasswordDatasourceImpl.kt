package com.fabianospdev.volunteerscompose.features.forgotpassword.data.datasources

import com.fabianospdev.volunteerscompose.core.helpers.exceptions.toRequestException
import com.fabianospdev.volunteerscompose.features.forgotpassword.data.models.ForgotPasswordResponseModel
import javax.inject.Inject

class ForgotPasswordDatasourceImpl @Inject constructor() : ForgotPasswordDatasource {
    override suspend fun requestPasswordReset(email: String): Result<ForgotPasswordResponseModel> {
        return try {
            Result.success(
                ForgotPasswordResponseModel(
                    message = "Enviamos as instruções de redefinição para $email"
                )
            )
        } catch (e: Exception) {
            Result.failure(e.toRequestException())
        }
    }
}

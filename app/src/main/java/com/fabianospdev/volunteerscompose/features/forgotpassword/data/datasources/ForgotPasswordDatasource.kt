package com.fabianospdev.volunteerscompose.features.forgotpassword.data.datasources

import com.fabianospdev.volunteerscompose.features.forgotpassword.data.models.ForgotPasswordResponseModel

interface ForgotPasswordDatasource {
    suspend fun requestPasswordReset(email: String): Result<ForgotPasswordResponseModel>
}

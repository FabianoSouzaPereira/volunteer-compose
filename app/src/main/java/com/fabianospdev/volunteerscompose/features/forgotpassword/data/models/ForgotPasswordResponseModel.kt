package com.fabianospdev.volunteerscompose.features.forgotpassword.data.models

import com.fabianospdev.volunteerscompose.features.forgotpassword.domain.entities.ForgotPasswordResponseEntity

data class ForgotPasswordResponseModel(
    val message: String
)

fun ForgotPasswordResponseModel.toEntity(): ForgotPasswordResponseEntity {
    return ForgotPasswordResponseEntity(message = message)
}

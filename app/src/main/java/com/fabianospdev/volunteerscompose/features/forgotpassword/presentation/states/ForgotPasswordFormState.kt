package com.fabianospdev.volunteerscompose.features.forgotpassword.presentation.states

data class ForgotPasswordFormState(
    val email: String = "",
    val emailError: String? = null
) {
    fun emailErrorMessage(): String? = when {
        email.isBlank() -> "E-mail é obrigatório"
        !email.contains("@") -> "E-mail inválido"
        email.length > 100 -> "E-mail muito longo"
        else -> null
    }
}

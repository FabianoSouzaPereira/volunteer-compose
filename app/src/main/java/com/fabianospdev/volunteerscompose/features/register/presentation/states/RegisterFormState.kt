package com.fabianospdev.volunteerscompose.features.register.presentation.states

data class RegisterFormState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val showPassword: Boolean = false
) {
    fun nameErrorMessage(): String? = when {
        name.isBlank() -> "Nome é obrigatório"
        name.trim().length < 2 -> "Nome deve ter pelo menos 2 caracteres"
        else -> null
    }

    fun emailErrorMessage(): String? = when {
        email.isBlank() -> "E-mail é obrigatório"
        !email.contains("@") -> "E-mail inválido"
        email.length > 100 -> "E-mail muito longo"
        else -> null
    }

    fun passwordErrorMessage(): String? = when {
        password.isBlank() -> "Senha é obrigatória"
        password.length < 6 -> "Senha deve ter pelo menos 6 caracteres"
        password.length > 50 -> "Senha muito longa"
        else -> null
    }

    fun confirmPasswordErrorMessage(): String? = when {
        confirmPassword.isBlank() -> "Confirme a senha"
        confirmPassword != password -> "As senhas não coincidem"
        else -> null
    }
}

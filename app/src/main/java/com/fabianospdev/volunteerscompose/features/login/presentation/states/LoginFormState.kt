package com.fabianospdev.volunteerscompose.features.login.presentation.states

data class LoginFormState(
    val username: String = "",
    val password: String = "",
    val usernameError: String? = null,
    val passwordError: String? = null,
    val showPassword: Boolean = false
) {
    val isUsernameValid: Boolean
        get() = username.isNotBlank() && username.contains("@") && username.length <= 100

    val isPasswordValid: Boolean
        get() = password.length in 6..50

    val isFormValid: Boolean
        get() = isUsernameValid && isPasswordValid

    fun usernameErrorMessage(): String? = when {
        username.isBlank() -> "E-mail é obrigatório"
        !username.contains("@") -> "E-mail inválido"
        username.length > 100 -> "E-mail muito longo"
        else -> null
    }

    fun passwordErrorMessage(): String? = when {
        password.isBlank() -> "Senha é obrigatória"
        password.length < 6 -> "Senha deve ter pelo menos 6 caracteres"
        password.length > 50 -> "Senha muito longa"
        else -> null
    }
}

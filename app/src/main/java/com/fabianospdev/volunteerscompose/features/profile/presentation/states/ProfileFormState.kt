package com.fabianospdev.volunteerscompose.features.profile.presentation.states

data class ProfileFormState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val nameError: String? = null
) {
    fun nameErrorMessage(): String? = when {
        name.isBlank() -> "Nome é obrigatório"
        name.trim().length < 2 -> "Nome deve ter pelo menos 2 caracteres"
        else -> null
    }
}

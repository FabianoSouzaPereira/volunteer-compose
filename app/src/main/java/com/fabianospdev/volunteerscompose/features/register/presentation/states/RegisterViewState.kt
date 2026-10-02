package com.fabianospdev.volunteerscompose.features.register.presentation.states

data class RegisterViewState(
    val screenState: RegisterState = RegisterState.RegisterIdle,
    val formState: RegisterFormState = RegisterFormState()
)

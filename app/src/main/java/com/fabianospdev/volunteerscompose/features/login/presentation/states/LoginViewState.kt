package com.fabianospdev.volunteerscompose.features.login.presentation.states

data class LoginViewState(
    val formState: LoginFormState = LoginFormState(),
    val screenState: LoginState = LoginState.LoginIdle
)
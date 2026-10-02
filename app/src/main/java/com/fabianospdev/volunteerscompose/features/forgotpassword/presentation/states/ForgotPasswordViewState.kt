package com.fabianospdev.volunteerscompose.features.forgotpassword.presentation.states

data class ForgotPasswordViewState(
    val screenState: ForgotPasswordState = ForgotPasswordState.ForgotPasswordIdle,
    val formState: ForgotPasswordFormState = ForgotPasswordFormState()
)

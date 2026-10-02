package com.fabianospdev.volunteerscompose.features.forgotpassword.presentation.states

sealed class ForgotPasswordNavigationEvent {
    object NavigateBack : ForgotPasswordNavigationEvent()
    object NavigateToLogin : ForgotPasswordNavigationEvent()
    data class NavigateToRoute(val route: String) : ForgotPasswordNavigationEvent()
}

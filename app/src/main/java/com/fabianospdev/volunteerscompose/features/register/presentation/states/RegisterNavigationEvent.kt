package com.fabianospdev.volunteerscompose.features.register.presentation.states

sealed class RegisterNavigationEvent {
    object NavigateBack : RegisterNavigationEvent()
    object NavigateToLogin : RegisterNavigationEvent()
    data class NavigateToRoute(val route: String) : RegisterNavigationEvent()
}

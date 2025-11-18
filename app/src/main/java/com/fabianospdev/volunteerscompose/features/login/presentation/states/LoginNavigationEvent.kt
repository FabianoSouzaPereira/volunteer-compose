package com.fabianospdev.volunteerscompose.features.login.presentation.states

/** Eventos de navegação específicos do Login */
sealed class LoginNavigationEvent {
    object NavigateToHome : LoginNavigationEvent()
    object NavigateToSettings : LoginNavigationEvent()
    object NavigateBack : LoginNavigationEvent()
    data class NavigateToRoute(val route: String) : LoginNavigationEvent()
}
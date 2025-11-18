package com.fabianospdev.volunteerscompose.features.splash.presentation

sealed class SplashNavigationEvent {
    object NavigateToLogin : SplashNavigationEvent()
    object NavigateToHome : SplashNavigationEvent()
    data class NavigateToRoute(val route: String) : SplashNavigationEvent()
}
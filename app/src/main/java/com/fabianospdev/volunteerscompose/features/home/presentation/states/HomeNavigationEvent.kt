package com.fabianospdev.volunteerscompose.features.home.presentation.states

sealed class HomeNavigationEvent {
    object NavigateToSettings : HomeNavigationEvent()
    object NavigateToProfile : HomeNavigationEvent()
    object NavigateBack : HomeNavigationEvent()
    object NavigateToLogin : HomeNavigationEvent()
    data class NavigateToRoute(val route: String) : HomeNavigationEvent()
}
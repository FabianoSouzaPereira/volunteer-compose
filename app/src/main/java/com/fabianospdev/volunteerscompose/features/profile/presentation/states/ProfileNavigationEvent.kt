package com.fabianospdev.volunteerscompose.features.profile.presentation.states

sealed class ProfileNavigationEvent {
    object NavigateBack : ProfileNavigationEvent()
    data class NavigateToRoute(val route: String) : ProfileNavigationEvent()
}

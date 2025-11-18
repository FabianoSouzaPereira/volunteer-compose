package com.fabianospdev.volunteerscompose.features.settings.presentation.states

sealed class SettingsNavigationEvent {
    object NavigateBack : SettingsNavigationEvent()
    object NavigateToHome : SettingsNavigationEvent()
    object NavigateToProfile : SettingsNavigationEvent()
    object NavigateToAbout : SettingsNavigationEvent()
    data class NavigateToRoute(val route: String) : SettingsNavigationEvent()
}
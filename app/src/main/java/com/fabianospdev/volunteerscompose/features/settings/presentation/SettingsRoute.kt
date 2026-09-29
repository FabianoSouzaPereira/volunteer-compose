package com.fabianospdev.volunteerscompose.features.settings.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabianospdev.volunteerscompose.features.settings.presentation.states.SettingsNavigationEvent

@Composable
fun SettingsRoute(onNavigationEvent: (SettingsNavigationEvent) -> Unit) {
    val viewModel: SettingsViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { event ->
            onNavigationEvent(event)
        }
    }

    SettingsScreen(
        viewState = viewState,
        onDarkModeToggle = viewModel::onDarkModeToggle,
        onNotificationsToggle = viewModel::onNotificationsToggle,
        onNavigateBack = viewModel::onNavigateBack,
        onNavigateToProfile = viewModel::onNavigateToProfile,
        onNavigateToAbout = viewModel::onNavigateToAbout,
        onLogout = viewModel::onLogout,
        onRetry = viewModel::onRetry
    )
}

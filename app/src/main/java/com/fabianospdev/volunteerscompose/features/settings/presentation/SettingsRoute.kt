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
    val state by viewModel.state.collectAsState()
    val isDarkModeEnabled by viewModel.isDarkModeEnabled.collectAsState()
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { event ->
            onNavigationEvent(event)
        }
    }

    SettingsScreen(
        state = state,
        isDarkModeEnabled = isDarkModeEnabled,
        notificationsEnabled = notificationsEnabled,
        onDarkModeToggle = viewModel::onDarkModeToggle,
        onNotificationsToggle = viewModel::onNotificationsToggle,
        onNavigateBack = viewModel::onNavigateBack,
        onNavigateToProfile = viewModel::onNavigateToProfile,
        onNavigateToAbout = viewModel::onNavigateToAbout,
        onLogout = viewModel::onLogout,
        onRetry = viewModel::onRetry
    )
}

package com.fabianospdev.volunteerscompose.features.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabianospdev.volunteerscompose.features.home.presentation.states.HomeActions
import com.fabianospdev.volunteerscompose.features.home.presentation.states.HomeNavigationEvent

@Composable
fun HomeRoute(onNavigationEvent: (HomeNavigationEvent) -> Unit) {
    val viewModel: HomeViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { event ->
            onNavigationEvent(event)
        }
    }

    val actions = remember(viewModel) {
        HomeActions(
            onNavigateToSettings = viewModel::onNavigateToSettings,
            onNavigateToProfile = viewModel::onNavigateToProfile,
            onNavigateToLogin = viewModel::onNavigateToLogin,
            onRetry = viewModel::onRetry
        )
    }

    HomeScreen(
        viewState = viewState,
        actions = actions
    )
}

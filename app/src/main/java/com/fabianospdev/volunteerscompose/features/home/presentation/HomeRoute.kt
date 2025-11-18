package com.fabianospdev.volunteerscompose.features.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabianospdev.volunteerscompose.features.home.presentation.states.HomeNavigationEvent

@Composable
fun HomeRoute(onNavigationEvent: (HomeNavigationEvent) -> Unit) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { event ->
            onNavigationEvent(event)
        }
    }

    HomeScreen(
        state = state,
        onNavigationEvent = { event ->
            /* Eventos que vêm diretamente da UI */
            when (event) {
                is HomeNavigationEvent.NavigateToSettings -> viewModel.onNavigateToSettings()
                is HomeNavigationEvent.NavigateToProfile -> viewModel.onNavigateToProfile()
                is HomeNavigationEvent.NavigateToLogin -> viewModel.onNavigateToLogin()
                is HomeNavigationEvent.NavigateBack -> viewModel.onNavigateBack()
                else -> onNavigationEvent(event)
            }
        },
        onRetry = viewModel::onRetry
    )
}
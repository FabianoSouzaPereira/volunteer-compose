package com.fabianospdev.volunteerscompose.features.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginNavigationEvent

@Composable
fun HomeRoute(onNavigationEvent: (LoginNavigationEvent) -> Unit) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value

    HomeScreen(
        state = state,
        onNavigationEvent = onNavigationEvent,
        onRetry = viewModel::onRetry
    )
}
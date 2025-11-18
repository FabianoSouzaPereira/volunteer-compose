package com.fabianospdev.volunteerscompose.features.login.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginNavigationEvent

@Composable
fun LoginRoute(onNavigationEvent: (LoginNavigationEvent) -> Unit) {
    val viewModel: LoginViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { event ->
            onNavigationEvent(event)
        }
    }

    LoginScreen(
        viewState = viewState,
        onLoginClick = viewModel::onLoginClick,
        onUsernameChange = viewModel::onUsernameChange,
        onPasswordChange = viewModel::onPasswordChange,
        onTogglePasswordVisibility = viewModel::onTogglePasswordVisibility,
        onRetry = viewModel::onRetry,
        onClearInputFields = viewModel::clearInputFields,
        onNavigationEvent = { event ->
            when (event) {
                is LoginNavigationEvent.NavigateToSettings -> {
                    viewModel.onNavigateToSettings()
                }
                else -> onNavigationEvent(event)
            }
        }
    )
}
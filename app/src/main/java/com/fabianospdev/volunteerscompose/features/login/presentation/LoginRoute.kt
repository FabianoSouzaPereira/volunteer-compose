package com.fabianospdev.volunteerscompose.features.login.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginActions
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

    val actions = remember(viewModel) {
        LoginActions(
            onLoginClick = viewModel::onLoginClick,
            onUsernameChange = viewModel::onUsernameChange,
            onPasswordChange = viewModel::onPasswordChange,
            onTogglePasswordVisibility = viewModel::onTogglePasswordVisibility,
            onRetry = viewModel::onRetry,
            onClearInputFields = viewModel::clearInputFields,
            onNavigateToForgotPassword = viewModel::onNavigateToForgotPassword,
            onNavigateToRegister = viewModel::onNavigateToRegister,
            onNavigateToSettings = viewModel::onNavigateToSettings
        )
    }

    LoginScreen(
        viewState = viewState,
        actions = actions
    )
}

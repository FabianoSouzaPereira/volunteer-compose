package com.fabianospdev.volunteerscompose.features.register.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabianospdev.volunteerscompose.features.register.presentation.states.RegisterActions
import com.fabianospdev.volunteerscompose.features.register.presentation.states.RegisterNavigationEvent

@Composable
fun RegisterRoute(onNavigationEvent: (RegisterNavigationEvent) -> Unit) {
    val viewModel: RegisterViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { event ->
            onNavigationEvent(event)
        }
    }

    val actions = remember(viewModel) {
        RegisterActions(
            onNameChange = viewModel::onNameChange,
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
            onTogglePasswordVisibility = viewModel::onTogglePasswordVisibility,
            onSubmit = viewModel::onSubmit,
            onRetry = viewModel::onRetry,
            onNavigateBack = viewModel::onNavigateBack,
            onNavigateToLogin = viewModel::onNavigateToLogin
        )
    }

    RegisterScreen(
        viewState = viewState,
        actions = actions
    )
}

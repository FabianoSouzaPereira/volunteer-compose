package com.fabianospdev.volunteerscompose.features.forgotpassword.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabianospdev.volunteerscompose.features.forgotpassword.presentation.states.ForgotPasswordActions
import com.fabianospdev.volunteerscompose.features.forgotpassword.presentation.states.ForgotPasswordNavigationEvent

@Composable
fun ForgotPasswordRoute(onNavigationEvent: (ForgotPasswordNavigationEvent) -> Unit) {
    val viewModel: ForgotPasswordViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { event ->
            onNavigationEvent(event)
        }
    }

    val actions = remember(viewModel) {
        ForgotPasswordActions(
            onEmailChange = viewModel::onEmailChange,
            onSubmit = viewModel::onSubmit,
            onRetry = viewModel::onRetry,
            onNavigateBack = viewModel::onNavigateBack,
            onNavigateToLogin = viewModel::onNavigateToLogin
        )
    }

    ForgotPasswordScreen(
        viewState = viewState,
        actions = actions
    )
}

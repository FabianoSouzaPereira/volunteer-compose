package com.fabianospdev.volunteerscompose.features.profile.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabianospdev.volunteerscompose.features.profile.presentation.states.ProfileActions
import com.fabianospdev.volunteerscompose.features.profile.presentation.states.ProfileNavigationEvent

@Composable
fun ProfileRoute(onNavigationEvent: (ProfileNavigationEvent) -> Unit) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { event ->
            onNavigationEvent(event)
        }
    }

    val actions = remember(viewModel) {
        ProfileActions(
            onNameChange = viewModel::onNameChange,
            onPhoneChange = viewModel::onPhoneChange,
            onSave = viewModel::onSave,
            onNavigateBack = viewModel::onNavigateBack,
            onRetry = viewModel::onRetry
        )
    }

    ProfileScreen(
        viewState = viewState,
        actions = actions
    )
}

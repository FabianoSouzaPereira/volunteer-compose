package com.fabianospdev.volunteerscompose.features.about.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabianospdev.volunteerscompose.features.about.presentation.states.AboutActions
import com.fabianospdev.volunteerscompose.features.about.presentation.states.AboutNavigationEvent

@Composable
fun AboutRoute(onNavigationEvent: (AboutNavigationEvent) -> Unit) {
    val viewModel: AboutViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { event ->
            onNavigationEvent(event)
        }
    }

    val actions = remember(viewModel) {
        AboutActions(onNavigateBack = viewModel::onNavigateBack)
    }

    AboutScreen(actions = actions)
}

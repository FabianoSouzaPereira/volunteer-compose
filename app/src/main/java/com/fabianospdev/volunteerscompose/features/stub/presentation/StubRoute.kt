package com.fabianospdev.volunteerscompose.features.stub.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabianospdev.volunteerscompose.core.presentation.StubScreen

@Composable
fun StubRoute(
    title: String,
    description: String = "Esta tela ainda não foi implementada.",
    onNavigationEvent: (StubNavigationEvent) -> Unit
) {
    val viewModel: StubViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { event ->
            onNavigationEvent(event)
        }
    }

    StubScreen(
        title = title,
        description = description,
        onNavigateBack = viewModel::onNavigateBack
    )
}

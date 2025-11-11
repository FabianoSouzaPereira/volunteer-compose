package com.fabianospdev.volunteerscompose.features.settings.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun SettingsRoute(navController: NavHostController) {
    val viewModel: SettingsViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value

    SettingsScreen(
        state = state,
        navController = navController
    )
}
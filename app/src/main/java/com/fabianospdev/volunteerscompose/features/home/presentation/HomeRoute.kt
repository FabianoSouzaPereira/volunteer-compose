package com.fabianospdev.volunteerscompose.features.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun HomeRoute(navController: NavHostController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value

    HomeScreen(
        state = state,
        navController = navController,
        onRetry = viewModel::onRetry
    )
}
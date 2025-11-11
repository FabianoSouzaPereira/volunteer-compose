package com.fabianospdev.volunteerscompose.features.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingsScreen(
    state: SettingsState,
    navController: androidx.navigation.NavHostController
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Pure Boost")
    }

    //TODO: Implement settings screen UI
}
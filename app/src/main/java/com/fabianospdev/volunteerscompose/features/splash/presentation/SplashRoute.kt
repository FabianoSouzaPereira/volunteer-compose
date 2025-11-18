package com.fabianospdev.volunteerscompose.features.splash.presentation


import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginNavigationEvent

@Composable
fun SplashRoute(onNavigationEvent: (SplashNavigationEvent) -> Unit) {
    SplashScreen(onNavigationEvent = onNavigationEvent)
}
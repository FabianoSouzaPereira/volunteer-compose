package com.fabianospdev.baseapp.features.splash.presentation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun SplashRoute(navController: NavHostController) {
    SplashScreen(navController = navController)
}
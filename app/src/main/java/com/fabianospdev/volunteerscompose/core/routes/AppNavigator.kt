package com.fabianospdev.volunteerscompose.core.routes

import androidx.navigation.NavHostController

class AppNavigator(private val navController: NavHostController) {

    fun navigateTo(route: String, popUpTo: String? = null) {
        navController.navigate(route) {
            popUpTo?.let {
                popUpTo(it) { inclusive = true }
            }
        }
    }

    fun popBackStack() {
        navController.popBackStack()
    }

    fun navigateToHome(popUpTo: String? = Routes.LOGIN) {
        navigateTo(Routes.HOME, popUpTo)
    }

    fun navigateToLogin(popUpTo: String? = Routes.SPLASH) {
        navigateTo(Routes.LOGIN, popUpTo)
    }

    fun navigateToSettings() {
        navigateTo(Routes.SETTINGS)
    }
}
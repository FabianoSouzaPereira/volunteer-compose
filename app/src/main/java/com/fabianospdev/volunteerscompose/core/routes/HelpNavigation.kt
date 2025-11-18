package com.fabianospdev.volunteerscompose.core.routes

import androidx.navigation.NavHostController

/** Utilitários de navegação. Funções utilitárias para criar os callbacks */
object NavigationHelper {

    fun createHomeNavigation(navController: NavHostController): () -> Unit = {
        navController.navigate(Routes.HOME) {
            popUpTo(Routes.LOGIN) { inclusive = true }
        }
    }

    fun createSettingsNavigation(navController: NavHostController): () -> Unit = {
        navController.navigate(Routes.SETTINGS)
    }

    fun createBackNavigation(navController: NavHostController): () -> Unit = {
        navController.popBackStack()
    }

    /* Para navegação genérica */
    fun createRouteNavigation(navController: NavHostController, route: String): () -> Unit = {
        { navController.navigate(route) }
    }
}
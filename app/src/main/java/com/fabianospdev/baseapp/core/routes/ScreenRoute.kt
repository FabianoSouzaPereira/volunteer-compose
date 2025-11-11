package com.fabianospdev.baseapp.core.routes

open class ScreenRoute(val route: String) {
    object Splash : ScreenRoute("splash")
    object Login : ScreenRoute("login")
    object Home : ScreenRoute("home")
    object Settings : ScreenRoute("settings")
}
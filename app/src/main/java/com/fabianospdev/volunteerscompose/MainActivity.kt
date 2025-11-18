package com.fabianospdev.volunteerscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fabianospdev.volunteerscompose.core.routes.NavigationHelper
import com.fabianospdev.volunteerscompose.core.routes.Routes
import com.fabianospdev.volunteerscompose.features.home.presentation.HomeRoute
import com.fabianospdev.volunteerscompose.features.home.presentation.states.HomeNavigationEvent
import com.fabianospdev.volunteerscompose.features.login.presentation.LoginRoute
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginNavigationEvent
import com.fabianospdev.volunteerscompose.features.settings.presentation.SettingsRoute
import com.fabianospdev.volunteerscompose.features.settings.presentation.states.SettingsNavigationEvent
import com.fabianospdev.volunteerscompose.features.splash.presentation.SplashNavigationEvent
import com.fabianospdev.volunteerscompose.features.splash.presentation.SplashRoute
import com.fabianospdev.volunteerscompose.ui.theme.BaseAppTheme
import dagger.hilt.android.AndroidEntryPoint


// MainActivity.kt - VERSÃO SIMPLIFICADA
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                tonalElevation = 5.dp
            ) {
                BaseAppTheme {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = Routes.SPLASH) {
                        composable(Routes.SPLASH) {
                            SplashRoute { event ->
                                when (event) {
                                    is SplashNavigationEvent.NavigateToLogin -> {
                                        navController.navigate(Routes.LOGIN) {
                                            popUpTo(Routes.SPLASH) { inclusive = true }
                                        }
                                    }
                                    is SplashNavigationEvent.NavigateToHome -> {
                                        navController.navigate(Routes.HOME) {
                                            popUpTo(Routes.LOGIN) { inclusive = true }
                                        }
                                    }
                                    is SplashNavigationEvent.NavigateToRoute ->  {
                                        navController.navigate(event.route)
                                    }
                                }
                            }
                        }
                        composable(Routes.LOGIN) {
                            LoginRoute { event ->
                                when (event) {
                                    is LoginNavigationEvent.NavigateToHome -> {
                                        navController.navigate(Routes.HOME) {
                                            popUpTo(Routes.LOGIN) { inclusive = true }
                                        }
                                    }
                                    is LoginNavigationEvent.NavigateToSettings -> {
                                        navController.navigate(Routes.SETTINGS)
                                    }
                                    is LoginNavigationEvent.NavigateToForgotPassword -> {
                                        navController.navigate("forgot_password")
                                    }
                                    is LoginNavigationEvent.NavigateToRegister -> {
                                        navController.navigate("register")
                                    }
                                    is LoginNavigationEvent.NavigateBack -> {
                                        navController.popBackStack()
                                    }

                                    is LoginNavigationEvent.NavigateToRoute -> {
                                        navController.navigate(event.route)
                                    }
                                }
                            }
                        }
                        composable(Routes.HOME) {
                            HomeRoute { event ->
                                when (event) {
                                    is HomeNavigationEvent.NavigateToSettings -> {
                                        navController.navigate(Routes.SETTINGS)
                                    }
                                    is HomeNavigationEvent.NavigateToProfile -> {
                                        navController.navigate("profile")
                                    }
                                    is HomeNavigationEvent.NavigateToLogin -> {
                                        navController.navigate(Routes.LOGIN) {
                                            popUpTo(Routes.HOME) { inclusive = true }
                                        }
                                    }
                                    is HomeNavigationEvent.NavigateBack -> {
                                        navController.popBackStack()
                                    }

                                    is HomeNavigationEvent.NavigateToRoute -> {
                                        navController.navigate(event.route)
                                    }
                                }
                            }
                        }
                        composable(Routes.SETTINGS) {
                            SettingsRoute { event ->
                                when (event) {
                                    is SettingsNavigationEvent.NavigateBack -> {
                                        navController.popBackStack()
                                    }
                                    is SettingsNavigationEvent.NavigateToHome -> {
                                        navController.navigate(Routes.HOME) {
                                            popUpTo(Routes.SETTINGS) { inclusive = true }
                                        }
                                    }
                                    is SettingsNavigationEvent.NavigateToProfile -> {
                                        navController.navigate("profile")
                                    }
                                    is SettingsNavigationEvent.NavigateToAbout -> {
                                        navController.navigate("about")
                                    }
                                    is SettingsNavigationEvent.NavigateToRoute -> {
                                        navController.navigate(event.route)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
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
import com.fabianospdev.volunteerscompose.features.login.presentation.LoginRoute
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginNavigationEvent
import com.fabianospdev.volunteerscompose.features.settings.presentation.SettingsRoute
import com.fabianospdev.volunteerscompose.features.splash.presentation.SplashRoute
import com.fabianospdev.volunteerscompose.ui.theme.BaseAppTheme
import dagger.hilt.android.AndroidEntryPoint


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
                                handleNavigationEvent(event, navController)
                            }
                        }
                        composable(Routes.LOGIN) {
                            LoginRoute { event ->
                                handleNavigationEvent(event, navController)
                            }
                        }
                        composable(Routes.HOME) {
                            HomeRoute { event ->
                                handleNavigationEvent(event, navController)
                            }
                        }
                        composable(Routes.SETTINGS) {
                            SettingsRoute { event ->
                                handleNavigationEvent(event, navController)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun handleNavigationEvent(event: LoginNavigationEvent, navController: NavHostController) {
        when (event) {
            is LoginNavigationEvent.NavigateToHome -> {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.LOGIN) { inclusive = true }
                }
            }
            is LoginNavigationEvent.NavigateToSettings -> {
                navController.navigate(Routes.SETTINGS)
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
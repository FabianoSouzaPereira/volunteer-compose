package com.fabianospdev.volunteerscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fabianospdev.volunteerscompose.core.routes.AppNavigator
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


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(modifier = Modifier.fillMaxSize(), tonalElevation = 5.dp) {
                BaseAppTheme {
                    val navController = rememberNavController()
                    val navigator = remember { AppNavigator(navController) }

                    NavHost(navController = navController, startDestination = Routes.SPLASH) {
                        composable(Routes.SPLASH) {
                            SplashRoute { event ->
                                when (event) {
                                    is SplashNavigationEvent.NavigateToLogin ->
                                        navigator.navigateToLogin(Routes.SPLASH)
                                    is SplashNavigationEvent.NavigateToHome ->
                                        navigator.navigateToHome(Routes.SPLASH)
                                    is SplashNavigationEvent.NavigateToRoute ->
                                        navigator.navigateTo(event.route)
                                }
                            }
                        }
                        composable(Routes.LOGIN) {
                            LoginRoute { event ->
                                when (event) {
                                    is LoginNavigationEvent.NavigateToHome ->
                                        navigator.navigateToHome(Routes.LOGIN)
                                    is LoginNavigationEvent.NavigateToSettings ->
                                        navigator.navigateToSettings()
                                    is LoginNavigationEvent.NavigateToForgotPassword ->
                                        navigator.navigateTo("forgot_password")
                                    is LoginNavigationEvent.NavigateToRegister ->
                                        navigator.navigateTo("register")
                                    is LoginNavigationEvent.NavigateBack ->
                                        navigator.popBackStack()
                                    is LoginNavigationEvent.NavigateToRoute ->
                                        navigator.navigateTo(event.route)
                                }
                            }
                        }
                        composable(Routes.HOME) {
                            HomeRoute { event ->
                                when (event) {
                                    is HomeNavigationEvent.NavigateToSettings ->
                                        navigator.navigateToSettings()
                                    is HomeNavigationEvent.NavigateToProfile ->
                                        navigator.navigateTo("profile")
                                    is HomeNavigationEvent.NavigateToLogin ->
                                        navigator.navigateToLogin(Routes.HOME)
                                    is HomeNavigationEvent.NavigateBack ->
                                        navigator.popBackStack()
                                    is HomeNavigationEvent.NavigateToRoute ->
                                        navigator.navigateTo(event.route)
                                }
                            }
                        }
                        composable(Routes.SETTINGS) {
                            SettingsRoute { event ->
                                when (event) {
                                    is SettingsNavigationEvent.NavigateBack ->
                                        navigator.popBackStack()
                                    is SettingsNavigationEvent.NavigateToHome ->
                                        navigator.navigateToHome(Routes.SETTINGS)
                                    is SettingsNavigationEvent.NavigateToProfile ->
                                        navigator.navigateTo("profile")
                                    is SettingsNavigationEvent.NavigateToAbout ->
                                        navigator.navigateTo("about")
                                    is SettingsNavigationEvent.NavigateToRoute ->
                                        navigator.navigateTo(event.route)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
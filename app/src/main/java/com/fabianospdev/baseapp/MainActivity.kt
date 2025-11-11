package com.fabianospdev.baseapp

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
import com.fabianospdev.baseapp.core.routes.Routes
import com.fabianospdev.baseapp.features.home.presentation.HomeRoute
import com.fabianospdev.baseapp.features.login.presentation.LoginRoute
import com.fabianospdev.baseapp.features.settings.presentation.SettingsRoute
import com.fabianospdev.baseapp.features.splash.presentation.SplashRoute
import com.fabianospdev.baseapp.ui.theme.BaseAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                tonalElevation = 5.dp
            ) {
                BaseAppTheme {
                    navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Routes.SPLASH) {
                        composable(Routes.SPLASH) {
                            SplashRoute(navController = navController)
                        }
                        composable(Routes.LOGIN) {
                            LoginRoute(navController = navController)
                        }
                        composable(Routes.HOME) {
                            HomeRoute(navController = navController)
                        }
                        composable(Routes.SETTINGS) {
                            SettingsRoute(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
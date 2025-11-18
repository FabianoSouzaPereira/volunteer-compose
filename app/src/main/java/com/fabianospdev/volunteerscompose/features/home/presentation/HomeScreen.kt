package com.fabianospdev.volunteerscompose.features.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fabianospdev.volunteerscompose.R
import com.fabianospdev.volunteerscompose.features.home.presentation.states.HomeNavigationEvent
import com.fabianospdev.volunteerscompose.features.home.presentation.states.HomeState
import com.fabianospdev.volunteerscompose.features.home.presentation.states.toErrorType
import com.fabianospdev.volunteerscompose.features.login.presentation.components.ShowErrorScreen
import com.fabianospdev.volunteerscompose.features.settings.presentation.SettingsState

@Composable
fun HomeScreen(
    state: HomeState,
    onNavigationEvent: (HomeNavigationEvent) -> Unit = {},
    onRetry: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            when (val s = state) {
                is HomeState.HomeLoading -> {
                    // ShowLoadingComponent()
                }

                is HomeState.HomeIdle -> {
                    HomeContent(
                        onNavigationEvent = onNavigationEvent,
                        name = "Android",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }

                is HomeState.HomeSuccess -> {
                    HomeContent(
                        onNavigationEvent = onNavigationEvent,
                        name = "Android",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }

                is HomeState.HomeError,
                is HomeState.HomeNoConnection,
                is HomeState.HomeTimeoutError,
                is HomeState.HomeUnauthorized,
                is HomeState.HomeValidationError,
                is HomeState.HomeUnknown -> {
                    val message = when (s) {
                        is HomeState.HomeError -> s.error
                        is HomeState.HomeNoConnection -> s.message
                        is HomeState.HomeTimeoutError -> s.message
                        is HomeState.HomeUnauthorized -> s.message
                        is HomeState.HomeValidationError -> s.message
                        is HomeState.HomeUnknown -> s.message
                        else -> stringResource(R.string.erro_desconhecido)
                    }

                    ShowErrorScreen(
                        type = s.toErrorType(),
                        message = message,
                        onRetry = onRetry
                    )
                }
            }
        }
    }
}

@Composable
fun HomeContent(
    onNavigationEvent: (HomeNavigationEvent) -> Unit = {},
    name: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
        ) {
            Text(
                text = "Home Screen",
                fontSize = MaterialTheme.typography.headlineMedium.fontSize
            )

            Button(
                onClick = { onNavigationEvent(HomeNavigationEvent.NavigateToSettings) },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Settings")
            }

            Button(
                onClick = { onNavigationEvent(HomeNavigationEvent.NavigateToProfile) },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Profile")
            }

            Button(
                onClick = { onNavigationEvent(HomeNavigationEvent.NavigateToLogin) },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Logout")
            }
        }
    }
}


/* Função auxiliar para mapear tipo de erro (adicione no mesmo arquivo) */
fun SettingsState.toErrorType(): com.fabianospdev.volunteerscompose.core.domain.models.ErrorType = when (this) {
    is SettingsState.SettingsNoConnection -> com.fabianospdev.volunteerscompose.core.domain.models.ErrorType.NETWORK
    is SettingsState.SettingsTimeoutError -> com.fabianospdev.volunteerscompose.core.domain.models.ErrorType.TIMEOUT
    is SettingsState.SettingsUnauthorized -> com.fabianospdev.volunteerscompose.core.domain.models.ErrorType.UNAUTHORIZED
    is SettingsState.SettingsValidationError -> com.fabianospdev.volunteerscompose.core.domain.models.ErrorType.VALIDATION
    else -> com.fabianospdev.volunteerscompose.core.domain.models.ErrorType.UNKNOWN
}
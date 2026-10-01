package com.fabianospdev.volunteerscompose.features.settings.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fabianospdev.volunteerscompose.R
import com.fabianospdev.volunteerscompose.features.login.presentation.components.ShowErrorScreen
import com.fabianospdev.volunteerscompose.features.settings.presentation.states.SettingsActions
import com.fabianospdev.volunteerscompose.features.settings.presentation.states.SettingsState
import com.fabianospdev.volunteerscompose.features.settings.presentation.states.SettingsViewState
import com.fabianospdev.volunteerscompose.features.settings.presentation.states.toErrorType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewState: SettingsViewState,
    actions: SettingsActions = SettingsActions()
) {
    val settings = viewState.settings

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configurações") },
                navigationIcon = {
                    IconButton(onClick = actions.onNavigateBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_arrow_back_24),
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when (val currentState = viewState.screenState) {
            is SettingsState.SettingsLoading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Carregando configurações...")
                }
            }

            is SettingsState.SettingsIdle,
            is SettingsState.SettingsSuccess -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    Card(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Aparência",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            ListItem(
                                leadingContent = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.outline_dark_mode_24),
                                        contentDescription = "Modo escuro"
                                    )
                                },
                                headlineContent = { Text("Modo Escuro") },
                                trailingContent = {
                                    Switch(
                                        checked = settings.darkMode,
                                        onCheckedChange = actions.onDarkModeToggle
                                    )
                                }
                            )
                        }
                    }

                    Card(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Notificações",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            ListItem(
                                leadingContent = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_emoji_emotions_24),
                                        contentDescription = "Notificações"
                                    )
                                },
                                headlineContent = { Text("Notificações") },
                                trailingContent = {
                                    Switch(
                                        checked = settings.notifications,
                                        onCheckedChange = actions.onNotificationsToggle
                                    )
                                }
                            )
                        }
                    }

                    Card(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Navegação",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = actions.onNavigateToProfile,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.exercise_48dp),
                                    contentDescription = "Perfil"
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Perfil")
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = actions.onNavigateToAbout,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_data_info_alert_24),
                                    contentDescription = "Sobre"
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Sobre")
                            }
                        }
                    }

                    Card(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Ações",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = actions.onLogout,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Sair")
                            }
                        }
                    }
                }
            }

            is SettingsState.SettingsError,
            is SettingsState.SettingsNoConnection,
            is SettingsState.SettingsTimeoutError,
            is SettingsState.SettingsUnauthorized,
            is SettingsState.SettingsValidationError,
            is SettingsState.SettingsUnknown -> {
                val message = when (currentState) {
                    is SettingsState.SettingsError -> currentState.error
                    is SettingsState.SettingsNoConnection -> currentState.errorMessage
                    is SettingsState.SettingsTimeoutError -> currentState.message
                    is SettingsState.SettingsUnauthorized -> currentState.message
                    is SettingsState.SettingsValidationError -> currentState.message
                    is SettingsState.SettingsUnknown -> currentState.message
                    else -> stringResource(R.string.erro_desconhecido)
                }

                ShowErrorScreen(
                    type = currentState.toErrorType(),
                    message = message,
                    onRetry = actions.onRetry
                )
            }
        }
    }
}

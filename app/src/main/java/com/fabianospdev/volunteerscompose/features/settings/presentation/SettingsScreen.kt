package com.fabianospdev.volunteerscompose.features.settings.presentation

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fabianospdev.volunteerscompose.R
import com.fabianospdev.volunteerscompose.features.login.presentation.components.ShowErrorScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsState,
    isDarkModeEnabled: Boolean,
    notificationsEnabled: Boolean,
    onDarkModeToggle: (Boolean) -> Unit,
    onNotificationsToggle: (Boolean) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onLogout: () -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configurações") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_arrow_back_24),
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when (val currentState = state) {
            is SettingsState.SettingsLoading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
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

                            androidx.compose.material3.ListItem(
                                leadingContent = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.outline_dark_mode_24),
                                        contentDescription = "Modo escuro"
                                    )
                                },
                                headlineContent = { Text("Modo Escuro") },
                                trailingContent = {
                                    Switch(
                                        checked = isDarkModeEnabled,
                                        onCheckedChange = onDarkModeToggle
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

                            // Notificações
                            androidx.compose.material3.ListItem(
                                leadingContent = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_emoji_emotions_24),
                                        contentDescription = "Notificações"
                                    )
                                },
                                headlineContent = { Text("Notificações") },
                                trailingContent = {
                                    Switch(
                                        checked = notificationsEnabled,
                                        onCheckedChange = onNotificationsToggle
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
                                onClick = onNavigateToProfile,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.exercise_48dp),
                                    contentDescription = "Notificações"
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Perfil")
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = onNavigateToAbout,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_data_info_alert_24),
                                    contentDescription = "Notificações"
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
                                onClick = onLogout,
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
                    onRetry = onRetry
                )
            }
        }
    }
}


fun SettingsState.toErrorType(): com.fabianospdev.volunteerscompose.core.domain.models.ErrorType = when (this) {
    is SettingsState.SettingsNoConnection -> com.fabianospdev.volunteerscompose.core.domain.models.ErrorType.NETWORK
    is SettingsState.SettingsTimeoutError -> com.fabianospdev.volunteerscompose.core.domain.models.ErrorType.TIMEOUT
    is SettingsState.SettingsUnauthorized -> com.fabianospdev.volunteerscompose.core.domain.models.ErrorType.UNAUTHORIZED
    is SettingsState.SettingsValidationError -> com.fabianospdev.volunteerscompose.core.domain.models.ErrorType.VALIDATION
    else -> com.fabianospdev.volunteerscompose.core.domain.models.ErrorType.UNKNOWN
}
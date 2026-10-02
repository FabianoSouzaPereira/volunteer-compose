package com.fabianospdev.volunteerscompose.features.about.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fabianospdev.volunteerscompose.R
import com.fabianospdev.volunteerscompose.features.about.presentation.states.AboutActions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    actions: AboutActions = AboutActions()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sobre") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            AboutCard(title = "Volunteers") {
                Text(
                    text = "App de voluntários em Kotlin e Jetpack Compose, organizado por feature. A navegação, o Hilt e o contrato de estado, eventos e camadas seguem o mesmo desenho em todas as telas.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            AboutCard(title = "Versão") {
                Text("Versão 1.0")
                Text("Pacote com.fabianospdev.volunteerscompose")
                Text("minSdk 29, targetSdk 36")
            }
            AboutCard(title = "Tecnologias") {
                Text("Kotlin e JDK 21")
                Text("Jetpack Compose e Material 3")
                Text("Navigation Compose")
                Text("Dagger Hilt")
                Text("Retrofit e OkHttp")
                Text("ViewModel, StateFlow e SharedFlow")
            }
        }
    }
}

@Composable
private fun AboutCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

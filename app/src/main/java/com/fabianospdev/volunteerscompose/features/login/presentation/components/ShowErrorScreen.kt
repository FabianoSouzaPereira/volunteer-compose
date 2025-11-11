package com.fabianospdev.volunteerscompose.features.login.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fabianospdev.volunteerscompose.core.domain.models.ErrorType

@Composable
fun ShowErrorScreen(
    type: ErrorType,
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = when (type) {
                ErrorType.NETWORK -> "Sem conexão"
                ErrorType.UNAUTHORIZED -> "Acesso negado"
                ErrorType.TIMEOUT -> "Tempo esgotado"
                else -> "Erro inesperado"
            },
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Button(onClick = onRetry) {
            Text("Tentar novamente")
        }
        Log.d("ShowLoginError", "Displayed error: $message")
    }
}

@Preview(showBackground = true)
@Composable
fun ShowErrorScreenPreview() {
    val navController = NavHostController(LocalContext.current)
    ShowErrorScreen(
        type = ErrorType.NETWORK,
        message = "Não foi possível conectar ao servidor. Verifique sua conexão com a internet.",
        onRetry = {}
    )
}

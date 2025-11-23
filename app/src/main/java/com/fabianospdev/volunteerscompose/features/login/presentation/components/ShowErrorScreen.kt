package com.fabianospdev.volunteerscompose.features.login.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fabianospdev.volunteerscompose.R
import com.fabianospdev.volunteerscompose.core.domain.models.ErrorType
import com.fabianospdev.volunteerscompose.ui.theme.appGradient

@Composable
fun ShowErrorScreen(
    type: ErrorType,
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .testTag("ErrorScreen")
            .fillMaxSize()
            .background(brush = MaterialTheme.appGradient)
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
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.surfaceVariant,
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Button(
            onClick = onRetry,
            modifier = Modifier
                .testTag(tag = "LoginErrorButton")
                .width(width = dimensionResource(R.dimen.button_width_medium))
                .padding(all = dimensionResource(R.dimen.button_padding))
                .clip(shape = RoundedCornerShape(dimensionResource(R.dimen.button_rounded_corner_shape)))
                .clip(shape = RoundedCornerShape(dimensionResource(R.dimen.button_rounded_corner_shape)))
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.surfaceVariant,
                            MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                )
                .border(
                    BorderStroke(
                        width = dimensionResource(R.dimen.button_border_size),
                        color = MaterialTheme.colorScheme.outlineVariant
                    ),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.button_rounded_corner_shape))
                ),
            contentPadding = ButtonDefaults.ContentPadding
        ) {
            Text("Tentar novamente")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowErrorScreenPreview() {
    ShowErrorScreen(
        type = ErrorType.NETWORK,
        message = "Não foi possível conectar ao servidor. Verifique sua conexão com a internet.",
        onRetry = {}
    )
}

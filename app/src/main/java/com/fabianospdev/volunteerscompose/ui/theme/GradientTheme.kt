package com.fabianospdev.volunteerscompose.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Main gradient of the app, used in several screens
val MaterialTheme.appGradient: Brush
    @Composable
    get() = Brush.verticalGradient(
        colors = listOf(
            colorScheme.primary,
            colorScheme.secondary
        )
    )

// (Optional) Background gradient used in some screens
val MaterialTheme.backgroundGradient: Brush
    @Composable
    get() = Brush.verticalGradient(
        colors = listOf(
            colorScheme.surface,
            colorScheme.primary.copy(alpha = 0.2f)
        )
    )

val MaterialTheme.progressIndicatorColor: Color
    @Composable
    get() = colorScheme.onPrimary
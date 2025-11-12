package com.fabianospdev.volunteerscompose.features.login.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.fabianospdev.volunteerscompose.ui.theme.appGradient
import com.fabianospdev.volunteerscompose.ui.theme.progressIndicatorColor
import kotlinx.coroutines.delay

@Composable
fun ShowLoginSuccess(
    navController: NavHostController
) {
    LaunchedEffect(key1 = Unit) {
        delay(timeMillis = 3000)
        navController.navigate(route = "home") {
            popUpTo(route = "login") { inclusive = true }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = MaterialTheme.appGradient)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login executado com sucesso!",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.testTag(tag = "LoginSuccess")
            )
            Spacer(modifier = Modifier.height(24.dp))
            CircularProgressIndicator(color = MaterialTheme.progressIndicatorColor)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ShowLoginSuccessPreview() {
    val context = LocalContext.current
    val navController = NavHostController(context)

    ShowLoginSuccess(
        navController = navController
    )
}
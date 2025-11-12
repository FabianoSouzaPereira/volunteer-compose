package com.fabianospdev.volunteerscompose.features.login.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fabianospdev.volunteerscompose.ui.theme.appGradient
import com.fabianospdev.volunteerscompose.ui.theme.progressIndicatorColor

@Composable
fun ShowLoginLoading(navController: NavHostController) {
    val LOGIN_LOADING_TEST_TAG = "LoginLoading"

    Box(
        modifier = Modifier
            .testTag(LOGIN_LOADING_TEST_TAG)
            .fillMaxSize()
            .background(MaterialTheme.appGradient)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.progressIndicatorColor)
    }
}


@Preview(showBackground = true)
@Composable
fun ShowLoginLoadingPreview() {
    val context = LocalContext.current
    val navController = NavHostController(context)
    ShowLoginLoading(navController)
}
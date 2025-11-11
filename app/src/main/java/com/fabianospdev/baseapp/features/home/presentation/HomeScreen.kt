package com.fabianospdev.baseapp.features.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fabianospdev.baseapp.R
import com.fabianospdev.baseapp.features.login.presentation.components.ShowErrorScreen

@Composable
fun HomeScreen(
    state: HomeState,
    navController: NavHostController,
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
                    //  ShowLoadingComponent()

                }

                is HomeState.HomeIdle -> {
                    HomeContent(
                        navController = navController,
                        name = "Android",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }

                is HomeState.HomeSuccess -> {
                    HomeContent(
                        navController = navController,
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
                        is HomeState.HomeError -> state.error
                        is HomeState.HomeNoConnection -> state.message
                        is HomeState.HomeTimeoutError -> state.message
                        is HomeState.HomeUnauthorized -> state.message
                        is HomeState.HomeValidationError -> state.message
                        is HomeState.HomeUnknown -> state.message
                        else -> stringResource(R.string.erro_desconhecido)
                    }

                    ShowErrorScreen(
                        type = state.toErrorType(),
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
    navController: NavHostController,
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Home Screen",
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    }
}
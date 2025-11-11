package com.fabianospdev.volunteerscompose.features.login.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun LoginRoute(navController: NavHostController) {
    val viewModel: LoginViewModel = hiltViewModel()
    val username by viewModel.username
    val password by viewModel.password
    val usernameError by viewModel.usernameError
    val passwordError by viewModel.passwordError
    val isFormValid by viewModel.isFormValid
    val state = viewModel.state.collectAsState().value

    LoginScreen(
        state = state,
        navController = navController,
        username = username,
        password = password,
        usernameError = usernameError,
        passwordError = passwordError,
        isFormValid = isFormValid,
        onLoginClick = viewModel::onLoginClick,
        onUsernameChange = viewModel::onUsernameChange,
        onPasswordChange = viewModel::onPasswordChange,
        onTogglePasswordVisibility = viewModel::onTogglePasswordVisibility,
        onRetry = viewModel::onRetry
    )
}
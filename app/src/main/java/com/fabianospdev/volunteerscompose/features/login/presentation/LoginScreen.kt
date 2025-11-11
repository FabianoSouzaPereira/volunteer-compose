package com.fabianospdev.volunteerscompose.features.login.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fabianospdev.volunteerscompose.R
import com.fabianospdev.volunteerscompose.features.login.presentation.components.ShowErrorScreen
import com.fabianospdev.volunteerscompose.features.login.presentation.components.ShowLoginIdle
import com.fabianospdev.volunteerscompose.features.login.presentation.components.ShowLoginLoading
import com.fabianospdev.volunteerscompose.features.login.presentation.components.ShowLoginSuccess
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginState
import com.fabianospdev.volunteerscompose.features.login.presentation.states.toErrorType

@Composable
fun LoginScreen(
    state: LoginState,
    navController: NavHostController,
    username: String,
    password: String,
    usernameError: String?,
    passwordError: String?,
    isFormValid: Boolean,
    onLoginClick: () -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        var showPassword by remember { mutableStateOf(false) }
        var focusRequester by remember { mutableStateOf(FocusRequester()) }
        val keyboardController = LocalSoftwareKeyboardController.current
        val gradient = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF2196F3),
                Color(0xFF21CBF3)
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            when (val s = state) {
                is LoginState.LoginLoading -> {
                    ShowLoginLoading(navController)
                }

                is LoginState.LoginIdle -> {
                    ShowLoginIdle(
                        username = username,
                        password = password,
                        usernameError = usernameError,
                        passwordError = passwordError,
                        showPassword = showPassword,
                        isFormValid = isFormValid,
                        gradient = gradient,
                        focusRequester = focusRequester,
                        keyboardController = keyboardController,
                        onLoginClick = onLoginClick,
                        onUsernameChange = onUsernameChange,
                        onPasswordChange = onPasswordChange,
                        onTogglePasswordVisibility = {
                            showPassword = !showPassword
                            onTogglePasswordVisibility
                        }
                    )
                }

                is LoginState.LoginSuccess -> {
                    ShowLoginSuccess(
                        navController = navController
                    )
                }

                is LoginState.LoginError,
                is LoginState.LoginNoConnection,
                is LoginState.LoginTimeoutError,
                is LoginState.LoginUnauthorized,
                is LoginState.LoginValidationError,
                is LoginState.LoginUnknown -> {
                    val message = when (s) {
                        is LoginState.LoginError -> state.error
                        is LoginState.LoginNoConnection -> state.message
                        is LoginState.LoginTimeoutError -> state.message
                        is LoginState.LoginUnauthorized -> state.message
                        is LoginState.LoginValidationError -> state.message
                        is LoginState.LoginUnknown -> state.message
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

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        state = LoginState.LoginIdle,
        navController = rememberNavController(),
        username = "Pablo",
        password = "123456",
        usernameError = null,
        passwordError = null,
        isFormValid = true,
        onLoginClick = {},
        onUsernameChange = {},
        onPasswordChange = {},
        onTogglePasswordVisibility = {},
        onRetry = {}
    )
}
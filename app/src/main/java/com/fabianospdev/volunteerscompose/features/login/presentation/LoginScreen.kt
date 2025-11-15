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
import com.fabianospdev.volunteerscompose.ui.theme.BaseAppTheme

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
    var showPassword by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

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
                        focusRequester = focusRequester,
                        keyboardController = keyboardController,
                        onLoginClick = onLoginClick,
                        onUsernameChange = onUsernameChange,
                        onPasswordChange = onPasswordChange,
                        onTogglePasswordVisibility = {
                            showPassword = !showPassword
                            onTogglePasswordVisibility()
                        }
                    )
                }

                is LoginState.LoginSuccess -> {
                    ShowLoginSuccess(navController)
                }

                is LoginState.LoginError,
                is LoginState.LoginNoConnection,
                is LoginState.LoginTimeoutError,
                is LoginState.LoginUnauthorized,
                is LoginState.LoginValidationError,
                is LoginState.LoginUnknown -> {

                    val message = when (s) {
                        is LoginState.LoginError -> s.error
                        is LoginState.LoginNoConnection -> s.message
                        is LoginState.LoginTimeoutError -> s.message
                        is LoginState.LoginUnauthorized -> s.message
                        is LoginState.LoginValidationError -> s.message
                        is LoginState.LoginUnknown -> s.message
                        else -> stringResource(R.string.erro_desconhecido)
                    }

                    ShowErrorScreen(
                        type = s.toErrorType(),
                        message = message,
                        onRetry = onRetry
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    BaseAppTheme {
        LoginScreen(
            state = LoginState.LoginIdle,
            navController = rememberNavController(),
            username = "Pablo",
            password = "123456",
            usernameError = null,
            passwordError = null,
            isFormValid = true,
            onLoginClick = { },
            onUsernameChange = {},
            onPasswordChange = {},
            onTogglePasswordVisibility = {},
            onRetry = {}
        )
    }
}
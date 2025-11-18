package com.fabianospdev.volunteerscompose.features.login.presentation

import android.R.attr.password
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
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginFormState
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginNavigationEvent
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginState
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginViewState
import com.fabianospdev.volunteerscompose.features.login.presentation.states.toErrorType
import com.fabianospdev.volunteerscompose.ui.theme.BaseAppTheme

@Composable
fun LoginScreen(
    viewState: LoginViewState, // ✅ Recebe o estado consolidado
    onLoginClick: () -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onRetry: () -> Unit,
    onNavigationEvent: (LoginNavigationEvent) -> Unit = {}
) {
    // Extrai os valores do viewState
    val formState = viewState.formState
    val screenState = viewState.screenState

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val state = screenState) {
                is LoginState.LoginLoading -> ShowLoginLoading()
                is LoginState.LoginIdle -> {
                    ShowLoginIdle(
                        formState = formState,
                        onLoginClick = onLoginClick,
                        onUsernameChange = onUsernameChange,
                        onPasswordChange = onPasswordChange,
                        onTogglePasswordVisibility = onTogglePasswordVisibility,
                        onNavigationEvent = onNavigationEvent
                    )
                }
                is LoginState.LoginSuccess -> ShowLoginSuccess()
                is LoginState.LoginError,
                is LoginState.LoginNoConnection,
                is LoginState.LoginTimeoutError,
                is LoginState.LoginUnauthorized,
                is LoginState.LoginValidationError,
                is LoginState.LoginUnknown -> {

                    val message = when (state) {
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

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val viewState = LoginViewState(
        formState = LoginFormState(),
        screenState = LoginState.LoginIdle
    )

    BaseAppTheme {
        LoginScreen(
            viewState = viewState,
            onLoginClick = { },
            onUsernameChange = {},
            onPasswordChange = {},
            onTogglePasswordVisibility = {},
            onRetry = {},
            onNavigationEvent = {}
        )
    }
}
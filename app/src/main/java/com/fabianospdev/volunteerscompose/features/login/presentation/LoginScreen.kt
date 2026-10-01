package com.fabianospdev.volunteerscompose.features.login.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fabianospdev.volunteerscompose.R
import com.fabianospdev.volunteerscompose.features.login.presentation.components.ShowErrorScreen
import com.fabianospdev.volunteerscompose.features.login.presentation.components.ShowLoginIdle
import com.fabianospdev.volunteerscompose.features.login.presentation.components.ShowLoginLoading
import com.fabianospdev.volunteerscompose.features.login.presentation.components.ShowLoginSuccess
import com.fabianospdev.volunteerscompose.features.login.presentation.components.ShowLoginSuccessPopup
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginActions
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginFormState
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginState
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginViewState
import com.fabianospdev.volunteerscompose.features.login.presentation.states.toErrorType
import com.fabianospdev.volunteerscompose.ui.theme.VolunteersTheme

@Composable
fun LoginScreen(
    viewState: LoginViewState,
    actions: LoginActions = LoginActions()
) {
    val formState = viewState.formState
    val screenState = viewState.screenState

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var showSuccessPopup by remember { mutableStateOf(false) }

    if (showSuccessPopup) {
        ShowLoginSuccessPopup(
            message = "Login realizado com sucesso!",
            onDismiss = { showSuccessPopup = false },
            imageResId = R.drawable.baseline_emoji_emotions_24,
            onAutoDismiss = actions.onClearInputFields
        )
    }

    LaunchedEffect(screenState) {
        if (screenState is LoginState.LoginSuccess) {
            showSuccessPopup = true
        }
    }

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
                        actions = actions,
                        focusRequester = focusRequester,
                        keyboardController = keyboardController
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
                        onRetry = actions.onRetry
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

    VolunteersTheme {
        LoginScreen(
            viewState = viewState,
            actions = LoginActions()
        )
    }
}

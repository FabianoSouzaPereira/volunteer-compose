package com.fabianospdev.volunteerscompose.features.forgotpassword.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fabianospdev.volunteerscompose.R
import com.fabianospdev.volunteerscompose.features.forgotpassword.presentation.states.ForgotPasswordActions
import com.fabianospdev.volunteerscompose.features.forgotpassword.presentation.states.ForgotPasswordState
import com.fabianospdev.volunteerscompose.features.forgotpassword.presentation.states.ForgotPasswordViewState
import com.fabianospdev.volunteerscompose.features.forgotpassword.presentation.states.errorText
import com.fabianospdev.volunteerscompose.features.forgotpassword.presentation.states.toErrorType
import com.fabianospdev.volunteerscompose.features.login.presentation.components.ShowErrorScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    viewState: ForgotPasswordViewState,
    actions: ForgotPasswordActions = ForgotPasswordActions()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Esqueci minha senha") },
                navigationIcon = {
                    IconButton(onClick = actions.onNavigateBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_arrow_back_24),
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when (val currentState = viewState.screenState) {
            is ForgotPasswordState.ForgotPasswordLoading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Enviando instruções...")
                }
            }

            is ForgotPasswordState.ForgotPasswordIdle -> {
                val form = viewState.formState
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Informe o e-mail da conta para receber as instruções de redefinição.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    OutlinedTextField(
                        value = form.email,
                        onValueChange = actions.onEmailChange,
                        label = { Text("E-mail") },
                        isError = form.emailError != null,
                        supportingText = form.emailError?.let { error -> { Text(error) } },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = actions.onSubmit,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Enviar")
                    }
                }
            }

            is ForgotPasswordState.ForgotPasswordSuccess -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = currentState.response.message,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = actions.onNavigateToLogin,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Voltar ao login")
                    }
                }
            }

            is ForgotPasswordState.ForgotPasswordError,
            is ForgotPasswordState.ForgotPasswordNoConnection,
            is ForgotPasswordState.ForgotPasswordTimeoutError,
            is ForgotPasswordState.ForgotPasswordUnauthorized,
            is ForgotPasswordState.ForgotPasswordValidationError,
            is ForgotPasswordState.ForgotPasswordUnknown -> {
                ShowErrorScreen(
                    type = currentState.toErrorType(),
                    message = currentState.errorText(),
                    onRetry = actions.onRetry
                )
            }
        }
    }
}

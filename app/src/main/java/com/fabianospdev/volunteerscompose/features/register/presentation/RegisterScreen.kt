package com.fabianospdev.volunteerscompose.features.register.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.fabianospdev.volunteerscompose.R
import com.fabianospdev.volunteerscompose.features.login.presentation.components.ShowErrorScreen
import com.fabianospdev.volunteerscompose.features.register.presentation.states.RegisterActions
import com.fabianospdev.volunteerscompose.features.register.presentation.states.RegisterFormState
import com.fabianospdev.volunteerscompose.features.register.presentation.states.RegisterState
import com.fabianospdev.volunteerscompose.features.register.presentation.states.RegisterViewState
import com.fabianospdev.volunteerscompose.features.register.presentation.states.errorText
import com.fabianospdev.volunteerscompose.features.register.presentation.states.toErrorType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewState: RegisterViewState,
    actions: RegisterActions = RegisterActions()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Criar conta") },
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
            is RegisterState.RegisterLoading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Criando conta...")
                }
            }

            is RegisterState.RegisterIdle -> {
                RegisterForm(
                    form = viewState.formState,
                    actions = actions,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp)
                )
            }

            is RegisterState.RegisterSuccess -> {
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
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = currentState.response.email,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = actions.onNavigateToLogin,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Ir para o login")
                    }
                }
            }

            is RegisterState.RegisterError,
            is RegisterState.RegisterNoConnection,
            is RegisterState.RegisterTimeoutError,
            is RegisterState.RegisterUnauthorized,
            is RegisterState.RegisterValidationError,
            is RegisterState.RegisterUnknown -> {
                ShowErrorScreen(
                    type = currentState.toErrorType(),
                    message = currentState.errorText(),
                    onRetry = actions.onRetry
                )
            }
        }
    }
}

@Composable
private fun RegisterForm(
    form: RegisterFormState,
    actions: RegisterActions,
    modifier: Modifier = Modifier
) {
    val visualTransformation = if (form.showPassword) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Preencha os dados para criar a conta.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = form.name,
            onValueChange = actions.onNameChange,
            label = { Text("Nome") },
            isError = form.nameError != null,
            supportingText = form.nameError?.let { error -> { Text(error) } },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = form.email,
            onValueChange = actions.onEmailChange,
            label = { Text("E-mail") },
            isError = form.emailError != null,
            supportingText = form.emailError?.let { error -> { Text(error) } },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = form.password,
            onValueChange = actions.onPasswordChange,
            label = { Text("Senha") },
            isError = form.passwordError != null,
            supportingText = form.passwordError?.let { error -> { Text(error) } },
            singleLine = true,
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            trailingIcon = {
                IconButton(onClick = actions.onTogglePasswordVisibility) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_visibility_off_24),
                        contentDescription = if (form.showPassword) "Ocultar senha" else "Mostrar senha"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = form.confirmPassword,
            onValueChange = actions.onConfirmPasswordChange,
            label = { Text("Confirmar senha") },
            isError = form.confirmPasswordError != null,
            supportingText = form.confirmPasswordError?.let { error -> { Text(error) } },
            singleLine = true,
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = actions.onSubmit,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Criar conta")
        }
    }
}

package com.fabianospdev.volunteerscompose.features.profile.presentation

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
import com.fabianospdev.volunteerscompose.features.login.presentation.components.ShowErrorScreen
import com.fabianospdev.volunteerscompose.features.profile.presentation.states.ProfileActions
import com.fabianospdev.volunteerscompose.features.profile.presentation.states.ProfileState
import com.fabianospdev.volunteerscompose.features.profile.presentation.states.ProfileViewState
import com.fabianospdev.volunteerscompose.features.profile.presentation.states.errorText
import com.fabianospdev.volunteerscompose.features.profile.presentation.states.toErrorType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewState: ProfileViewState,
    actions: ProfileActions = ProfileActions()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil") },
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
            is ProfileState.ProfileLoading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Carregando perfil...")
                }
            }

            is ProfileState.ProfileIdle,
            is ProfileState.ProfileSuccess -> {
                val form = viewState.formState
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState())
                ) {
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
                        onValueChange = {},
                        label = { Text("E-mail") },
                        readOnly = true,
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = form.phone,
                        onValueChange = actions.onPhoneChange,
                        label = { Text("Telefone") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = actions.onSave,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Salvar")
                    }
                }
            }

            is ProfileState.ProfileError,
            is ProfileState.ProfileNoConnection,
            is ProfileState.ProfileTimeoutError,
            is ProfileState.ProfileUnauthorized,
            is ProfileState.ProfileValidationError,
            is ProfileState.ProfileUnknown -> {
                ShowErrorScreen(
                    type = currentState.toErrorType(),
                    message = currentState.errorText(),
                    onRetry = actions.onRetry
                )
            }
        }
    }
}

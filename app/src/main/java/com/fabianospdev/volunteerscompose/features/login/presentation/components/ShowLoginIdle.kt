package com.fabianospdev.volunteerscompose.features.login.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fabianospdev.volunteerscompose.R
import com.fabianospdev.volunteerscompose.core.utils.LoadFontsFamily
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginFormState
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginNavigationEvent
import com.fabianospdev.volunteerscompose.features.login.presentation.utils.isRunningRoboletric
import com.fabianospdev.volunteerscompose.ui.theme.appGradient

@Composable
fun ShowLoginIdle(
    formState: LoginFormState,
    onLoginClick: () -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
    onNavigationEvent: (LoginNavigationEvent) -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 5.dp
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = MaterialTheme.appGradient)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (!isRunningRoboletric()) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Mindflow logo",
                        modifier = Modifier
                            .testTag("imageLogoField")
                            .size(100.dp, 100.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.large_space_betwing_elements)))

                Text(
                    modifier = Modifier.testTag("LoginIdleTitle"),
                    text = stringResource(R.string.login),
                    fontSize = with(LocalDensity.current) {
                        dimensionResource(id = R.dimen.title_font_text_size).value.sp
                    },
                    fontFamily = LoadFontsFamily.karlaFamily,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontStyle = FontStyle.Normal
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium_space_betwing_elements)))


                TextField(
                    value = formState.username,
                    onValueChange = onUsernameChange,
                    label = { Text(stringResource(R.string.email)) },
                    isError = formState.usernameError != null,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequester.requestFocus() }
                    ),
                    textStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                    shape = RoundedCornerShape(25),
                    modifier = Modifier
                        .testTag("userNameTextField")
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 6.dp)
                        .border(
                            width = dimensionResource(R.dimen.textfield_border_size),
                            color = if (formState.usernameError != null) Color.Red else MaterialTheme.colorScheme.onPrimary,
                            shape = RoundedCornerShape(dimensionResource(R.dimen.textfield_rounded_corner_shape))
                        )
                        .clip(RoundedCornerShape(dimensionResource(R.dimen.textfield_rounded_corner_shape)))
                        .focusRequester(focusRequester),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.email_email_com),
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            ),
                            maxLines = 1
                        )
                    },
                    maxLines = 1
                )


                if (formState.usernameError != null) {
                    Text(
                        text = formState.usernameError,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .testTag("userNameErrorTextField")
                            .align(Alignment.Start)
                            .padding(start = 32.dp, bottom = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium_space_betwing_elements)))


                TextField(
                    value = formState.password,
                    onValueChange = onPasswordChange,
                    label = { Text(stringResource(R.string.password)) },
                    isError = formState.passwordError != null,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    visualTransformation = if (formState.showPassword) VisualTransformation.None else
                        PasswordVisualTransformation(),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.None
                    ),
                    shape = RoundedCornerShape(25),
                    modifier = Modifier
                        .testTag("passwordTextField")
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 6.dp)
                        .border(
                            width = dimensionResource(R.dimen.textfield_border_size),
                            color = if (formState.passwordError != null) Color.Red else MaterialTheme.colorScheme.onPrimary,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .focusRequester(focusRequester),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.q_nm_44u),
                            fontSize = 16.sp,
                            fontFamily = LoadFontsFamily.montserratFamily,
                            fontWeight = FontWeight.Normal,
                            style = LocalTextStyle.current.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                            ),
                            maxLines = 1
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_check_circle),
                            contentDescription = "Check",
                            tint = if (formState.isFormValid) Color.Green else if (formState.passwordError != null) Color.Red else Color.Gray
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = onTogglePasswordVisibility, modifier = Modifier.testTag("TogglePasswordVisibility")) {
                            val iconId = if (formState.showPassword) {
                                R.drawable.baseline_remove_red_eye_24
                            } else {
                                R.drawable.baseline_visibility_off_24
                            }
                            Icon(
                                painter = painterResource(id = iconId),
                                contentDescription = if (formState.showPassword)
                                    stringResource(id = R.string.hide_password)
                                else
                                    stringResource(id = R.string.show_password),
                                tint = if (formState.passwordError != null) Color.Red else if (formState.showPassword) Color.Blue else Color.Gray
                            )
                        }
                    },
                    maxLines = 1
                )


                if (formState.passwordError != null) {
                    Text(
                        text = formState.passwordError,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 32.dp, bottom = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.large_space_betwing_elements)))


                Button(
                    onClick = { onLoginClick() },
                    enabled = formState.isFormValid,
                    interactionSource = remember { MutableInteractionSource() },
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier
                        .testTag(tag = "LoginButton")
                        .width(width = dimensionResource(R.dimen.button_width_medium))
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                        .clip(shape = RoundedCornerShape(dimensionResource(R.dimen.button_rounded_corner_shape)))
                        .background(
                            brush = if (!formState.isFormValid) Brush.verticalGradient(
                                listOf(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) else MaterialTheme.appGradient
                        )
                        .border(
                            BorderStroke(
                                width = dimensionResource(R.dimen.button_border_size),
                                color = if (!formState.isFormValid) MaterialTheme.colorScheme.outlineVariant else
                                    MaterialTheme.colorScheme.onPrimary
                            ),
                            shape = RoundedCornerShape(dimensionResource(R.dimen.button_rounded_corner_shape))
                        ),
                    contentPadding = ButtonDefaults.ContentPadding
                ) {
                    Text(
                        text = stringResource(R.string.login),
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        color = if (!formState.isFormValid) MaterialTheme.colorScheme.outline else
                            MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium_space_betwing_elements)))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    TextButton(
                        onClick = {
                            onNavigationEvent(LoginNavigationEvent.NavigateToForgotPassword)
                        },
                        modifier = Modifier.testTag("forgotPasswordButton")
                    ) {
                        Text(
                            text = "Esqueci minha senha",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 14.sp
                        )
                    }

                    TextButton(
                        onClick = {
                            onNavigationEvent(LoginNavigationEvent.NavigateToRegister)
                        },
                        modifier = Modifier.testTag("registerButton")
                    ) {
                        Text(
                            text = "Criar conta",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 14.sp
                        )
                    }

                    TextButton(
                        onClick = {
                            onNavigationEvent(LoginNavigationEvent.NavigateToSettings)
                        },
                        modifier = Modifier.testTag("settingsButton")
                    ) {
                        Text(
                            text = "Configurações",
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium_space_betwing_elements)))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowLoginIdlePreview() {
    val focusRequester = remember { FocusRequester() }

    val sampleFormState = LoginFormState(
        username = "user@example.com",
        password = "password123",
        usernameError = null,
        passwordError = null,
        isFormValid = true,
        showPassword = false
    )

    ShowLoginIdle(
        formState = sampleFormState,
        onLoginClick = { },
        onUsernameChange = {},
        onPasswordChange = {},
        onTogglePasswordVisibility = {},
        focusRequester = focusRequester,
        keyboardController = null
    )
}
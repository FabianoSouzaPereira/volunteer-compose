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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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

@Composable
fun ShowLoginIdle(
    username: String,
    password: String,
    usernameError: String?,
    passwordError: String?,
    showPassword: Boolean,
    isFormValid: Boolean,
    gradient: Brush,
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
    onLoginClick: () -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 5.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradient)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(300.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.login),
                    fontSize = with(LocalDensity.current) {
                        dimensionResource(id = R.dimen.title_font_text_size).value.sp
                    },
                    fontFamily = LoadFontsFamily.karlaFamily,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontStyle = FontStyle.Normal
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.minium_space_betwing_elements)))
                TextField(
                    value = username,
                    onValueChange = onUsernameChange,
                    label = { Text(stringResource(R.string.email)) },
                    isError = usernameError != null,
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
                        .padding(20.dp, 20.dp, 20.dp, 1.dp)
                        .border(
                            width = dimensionResource(R.dimen.textfield_border_size),
                            color = if (usernameError != null) Color.Red else MaterialTheme.colorScheme.onPrimary,
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
                TextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = { Text(stringResource(R.string.password)) },
                    isError = passwordError != null,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    visualTransformation = if (showPassword) VisualTransformation.None else
                        PasswordVisualTransformation(),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.None
                    ),
                    shape = RoundedCornerShape(25),
                    modifier = Modifier
                        .padding(20.dp, 6.dp, 20.dp, 20.dp)
                        .border(
                            width = dimensionResource(R.dimen.textfield_border_size),
                            color = if (passwordError != null) Color.Red else MaterialTheme.colorScheme.onPrimary,
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
                        isFormValid
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_check_circle),
                            contentDescription = "Check",
                            tint = if (isFormValid) Color.Green else if (passwordError != null) Color.Red else Color.Gray
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = onTogglePasswordVisibility) {
                            val icon: ImageVector = if (showPassword) {
                                ImageVector.vectorResource(id = R.drawable.baseline_remove_red_eye_24)
                            } else {
                                ImageVector.vectorResource(id = R.drawable.baseline_visibility_off_24)
                            }
                            Icon(
                                imageVector = icon,
                                contentDescription = if (showPassword)
                                    stringResource(id = R.string.hide_password)
                                else
                                    stringResource(id = R.string.show_password),
                                tint = if (passwordError != null) Color.Red else if (showPassword) Color.Blue else Color.Gray
                            )
                        }
                    },
                    maxLines = 1
                )
                if (passwordError != null) {
                    Text(
                        text = passwordError,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 32.dp, bottom = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { onLoginClick() },
                    enabled = isFormValid,
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
                        .width(dimensionResource(R.dimen.button_width_medium))
                        .padding(dimensionResource(R.dimen.button_padding))
                        .clip(RoundedCornerShape(dimensionResource(R.dimen.button_rounded_corner_shape)))
                        .background(
                            brush = if (!isFormValid) Brush.verticalGradient(
                                listOf(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) else gradient
                        )
                        .border(
                            BorderStroke(
                                width = dimensionResource(R.dimen.button_border_size),
                                color = if (!isFormValid) MaterialTheme.colorScheme.outlineVariant else
                                    MaterialTheme.colorScheme.onPrimary
                            ),
                            shape = RoundedCornerShape(dimensionResource(R.dimen.button_rounded_corner_shape))
                        ),
                    contentPadding = ButtonDefaults.ContentPadding
                ) {
                    Text(
                        text = stringResource(R.string.login),
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        color = if (!isFormValid) MaterialTheme.colorScheme.outline else
                            MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowLoginIdlePreview() {
    val focusRequester = remember { FocusRequester() }

    ShowLoginIdle(
        username = "",
        password = "",
        usernameError = null,
        passwordError = null,
        showPassword = false,
        isFormValid = false,
        gradient = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF2196F3),
                Color(0xFF21CBF3)
            )
        ),
        focusRequester = focusRequester,
        keyboardController = null,
        onLoginClick = { },
        onUsernameChange = {},
        onPasswordChange = {},
        onTogglePasswordVisibility = {}
    )
}
package com.fabianospdev.volunteerscompose.features.login.presentation

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fabianospdev.volunteerscompose.features.login.domain.entities.LoginResponseEntity
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [33])
@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setup() {

        if (Build.FINGERPRINT == null) {
            val field = Build::class.java.getDeclaredField("FINGERPRINT")
            field.isAccessible = true
            field.set(null, "robolectric")
        }
    }

    @Test
    fun whenStateIsIdle_shouldShowLoginIdle() {
        composeRule.setContent {
            LoginScreen(
                state = LoginState.LoginIdle,
                navController = rememberNavController(),
                username = "fabiano",
                password = "1234",
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

        // Verifica se o composable de LoginIdle foi mostrado
        // supondo que ShowLoginIdle tenha algum texto visível ou tag
        composeRule.onNodeWithTag("LoginIdle").assertExists()
    }


    @Test
    fun whenStateIsLoading_shouldShowLoading() {
        composeRule.setContent {
            LoginScreen(
                state = LoginState.LoginLoading,
                navController = rememberNavController(),
                username = "",
                password = "",
                usernameError = null,
                passwordError = null,
                isFormValid = false,
                onLoginClick = {},
                onUsernameChange = {},
                onPasswordChange = {},
                onTogglePasswordVisibility = {},
                onRetry = {}
            )
        }

        composeRule.onNodeWithTag("LoginLoading").assertExists()
    }

    @Test
    fun whenStateIsSuccess_shouldShowSuccessScreen() {
        composeRule.setContent {
            val entity = LoginResponseEntity(id = "1", name = "Fabiano", email = "", token = "abc123")
            LoginScreen(
                state = LoginState.LoginSuccess(entity),
                navController = rememberNavController(),
                username = "",
                password = "",
                usernameError = null,
                passwordError = null,
                isFormValid = false,
                onLoginClick = {},
                onUsernameChange = {},
                onPasswordChange = {},
                onTogglePasswordVisibility = {},
                onRetry = {}
            )
        }

        composeRule.onNodeWithTag("LoginSuccess").assertExists()
    }

    @Test
    fun whenStateIsError_shouldShowErrorScreen() {
        composeRule.setContent {
            LoginScreen(
                state = LoginState.LoginError("Erro de rede"),
                navController = rememberNavController(),
                username = "",
                password = "",
                usernameError = null,
                passwordError = null,
                isFormValid = false,
                onLoginClick = {},
                onUsernameChange = {},
                onPasswordChange = {},
                onTogglePasswordVisibility = {},
                onRetry = {}
            )
        }

        composeRule.onNodeWithTag("ErrorScreen").assertExists()
    }

    @Test
    fun whenClickLoginButton_shouldTriggerCallback() {
        var clicked = false

        composeRule.setContent {
            LoginScreen(
                state = LoginState.LoginIdle,
                navController = rememberNavController(),
                username = "fabiano",
                password = "1234",
                usernameError = null,
                passwordError = null,
                isFormValid = true,
                onLoginClick = { clicked = true },
                onUsernameChange = {},
                onPasswordChange = {},
                onTogglePasswordVisibility = {},
                onRetry = {}
            )
        }

        // Supondo que o botão de login tenha tag "LoginButton"
        composeRule.onNodeWithTag("LoginButton").performClick()

        assert(clicked)
    }

    @Test
    fun loginScreen() {
    }

    @Test
    fun loginContent() {
    }

}
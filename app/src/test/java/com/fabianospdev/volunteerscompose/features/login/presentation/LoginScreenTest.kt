package com.fabianospdev.volunteerscompose.features.login.presentation

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.test.printToString
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fabianospdev.volunteerscompose.features.login.domain.entities.LoginResponseEntity
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginState
import com.fabianospdev.volunteerscompose.ui.theme.BaseAppTheme
import org.junit.Assert.assertTrue
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
            BaseAppTheme {
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
        }

        composeRule.onNodeWithTag(testTag = "LoginIdleTitle").assertExists()
    }

    @Test
    fun whenStateIsLoading_shouldShowLoading() {
        composeRule.setContent {
            BaseAppTheme {
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
        }

        composeRule.onNodeWithTag(testTag = "LoginLoading").assertExists()
    }

    @Test
    fun whenStateIsSuccess_shouldShowSuccessScreen() {
        composeRule.setContent {
            val entity = LoginResponseEntity(id = "1", name = "Fabiano", email = "", token = "abc123")
            BaseAppTheme {
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
        }

        composeRule.onNodeWithTag("LoginSuccess").assertExists()
    }

    @Test
    fun whenStateIsError_shouldShowErrorScreen() {
        composeRule.setContent {
            BaseAppTheme {
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
        }

        composeRule.onNodeWithTag("ErrorScreen").assertExists()
    }

    @Test
    fun whenStateIsIdleShowLoginTitle() {
        composeRule.setContent {
            BaseAppTheme {
                LoginScreen(
                    state = LoginState.LoginIdle,
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
        }

        composeRule.onNodeWithTag("LoginIdleTitle").assertExists().assertIsDisplayed()
    }

    @Test
    fun whenStateIsIdleShowUserNameField() {
        composeRule.setContent {
            BaseAppTheme {
                LoginScreen(
                    state = LoginState.LoginIdle,
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
        }

        composeRule.onNodeWithTag("userNameTextField").assertExists().assertIsDisplayed()
    }

    @Test
    fun whenStateIsIdleShowPasswordField() {
        composeRule.setContent {
            BaseAppTheme {
                LoginScreen(
                    state = LoginState.LoginIdle,
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
        }

        composeRule.onNodeWithTag("passwordTextField").assertExists().assertIsDisplayed()
    }

    @Test
    fun whenFormIsValid_shouldEnableLoginButton() {
        composeRule.setContent {
            //BaseAppTheme {
                Box(Modifier.size(1080.dp, 1920.dp)) {
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
           // }
        }
        println(composeRule.onRoot().printToString())
        composeRule.onNodeWithTag("LoginButton", useUnmergedTree = true)
            .assertExists()
            .printToLog("DEBUG_TREE")
        composeRule.onNodeWithTag("LoginButton", useUnmergedTree = true)
            .assertExists()
            .assertIsDisplayed()
            .assertIsEnabled()
    }




    @Test
    fun whenClickLoginButton_shouldTriggerCallback() {
        var clicked = false

        composeRule.setContent {
            BaseAppTheme {
                Box(Modifier.size(1080.dp, 1920.dp)) {
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
            }
        }
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("LoginButton", useUnmergedTree = true)
            .assertExists()
            .printToLog("DEBUG_TREE")
        composeRule.onNodeWithTag("LoginButton", useUnmergedTree = false)
            .assertExists()
            .assertIsDisplayed()
            .performClick()
        composeRule.waitForIdle()

        assertTrue(clicked)
    }

    @Test
    fun loginScreen() {
    }

    @Test
    fun loginContent() {
    }

}
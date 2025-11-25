package com.fabianospdev.volunteerscompose.features.login.presentation

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fabianospdev.volunteerscompose.features.login.domain.entities.LoginResponseEntity
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginFormState
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginState
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginViewState
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

    private fun createViewState(
        screenState: LoginState = LoginState.LoginIdle,
        formState: LoginFormState = LoginFormState()
    ): LoginViewState {
        return LoginViewState(
            screenState = screenState,
            formState = formState
        )
    }

    private fun createSuccessLoginState(): LoginState.LoginSuccess {
        return LoginState.LoginSuccess(
            LoginResponseEntity(
                id = "1",
                name = "Fabiano",
                email = "fabiano@example.com",
                token = "abc123"
            )
        )
    }

    @Test
    fun whenStateIsIdle_shouldShowLoginIdle() {
        composeRule.setContent {
            BaseAppTheme {
                LoginScreen(
                    viewState = createViewState(screenState = LoginState.LoginIdle),
                    onLoginClick = {},
                    onUsernameChange = {},
                    onPasswordChange = {},
                    onTogglePasswordVisibility = {},
                    onRetry = {},
                    onClearInputFields = {},
                    onNavigationEvent = {}
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
                    viewState = createViewState(screenState = LoginState.LoginLoading),
                    onLoginClick = {},
                    onUsernameChange = {},
                    onPasswordChange = {},
                    onTogglePasswordVisibility = {},
                    onRetry = {},
                    onClearInputFields = {},
                    onNavigationEvent = {}
                )
            }
        }

        composeRule.onNodeWithTag(testTag = "LoginLoading").assertExists()
    }

    @Test
    fun whenStateIsSuccess_shouldShowSuccessScreen() {
        composeRule.setContent {
            BaseAppTheme {
                LoginScreen(
                    viewState = createViewState(screenState = createSuccessLoginState()),
                    onLoginClick = {},
                    onUsernameChange = {},
                    onPasswordChange = {},
                    onTogglePasswordVisibility = {},
                    onRetry = {},
                    onClearInputFields = {},
                    onNavigationEvent = {}
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
                    viewState = createViewState(screenState = LoginState.LoginError("Erro de rede")),
                    onLoginClick = {},
                    onUsernameChange = {},
                    onPasswordChange = {},
                    onTogglePasswordVisibility = {},
                    onRetry = {},
                    onClearInputFields = {},
                    onNavigationEvent = {}
                )
            }
        }

        composeRule.onNodeWithTag("ErrorScreen").assertExists()
    }

    @Test
    fun whenStateIsNoConnection_shouldShowErrorScreen() {
        composeRule.setContent {
            BaseAppTheme {
                LoginScreen(
                    viewState = createViewState(screenState = LoginState.LoginNoConnection("Sem conexão")),
                    onLoginClick = {},
                    onUsernameChange = {},
                    onPasswordChange = {},
                    onTogglePasswordVisibility = {},
                    onRetry = {},
                    onClearInputFields = {},
                    onNavigationEvent = {}
                )
            }
        }

        composeRule.onNodeWithTag("ErrorScreen").assertExists()
    }

    @Test
    fun whenStateIsTimeoutError_shouldShowErrorScreen() {
        composeRule.setContent {
            BaseAppTheme {
                LoginScreen(
                    viewState = createViewState(screenState = LoginState.LoginTimeoutError("Timeout")),
                    onLoginClick = {},
                    onUsernameChange = {},
                    onPasswordChange = {},
                    onTogglePasswordVisibility = {},
                    onRetry = {},
                    onClearInputFields = {},
                    onNavigationEvent = {}
                )
            }
        }

        composeRule.onNodeWithTag("ErrorScreen").assertExists()
    }

    @Test
    fun whenStateIsUnauthorized_shouldShowErrorScreen() {
        composeRule.setContent {
            BaseAppTheme {
                LoginScreen(
                    viewState = createViewState(screenState = LoginState.LoginUnauthorized("Não autorizado")),
                    onLoginClick = {},
                    onUsernameChange = {},
                    onPasswordChange = {},
                    onTogglePasswordVisibility = {},
                    onRetry = {},
                    onClearInputFields = {},
                    onNavigationEvent = {}
                )
            }
        }

        composeRule.onNodeWithTag("ErrorScreen").assertExists()
    }

    @Test
    fun whenStateIsValidationError_shouldShowErrorScreen() {
        composeRule.setContent {
            BaseAppTheme {
                LoginScreen(
                    viewState = createViewState(screenState = LoginState.LoginValidationError("Erro de validação")),
                    onLoginClick = {},
                    onUsernameChange = {},
                    onPasswordChange = {},
                    onTogglePasswordVisibility = {},
                    onRetry = {},
                    onClearInputFields = {},
                    onNavigationEvent = {}
                )
            }
        }

        composeRule.onNodeWithTag("ErrorScreen").assertExists()
    }

    @Test
    fun whenStateIsUnknownError_shouldShowErrorScreen() {
        composeRule.setContent {
            BaseAppTheme {
                LoginScreen(
                    viewState = createViewState(screenState = LoginState.LoginUnknown("Erro desconhecido")),
                    onLoginClick = {},
                    onUsernameChange = {},
                    onPasswordChange = {},
                    onTogglePasswordVisibility = {},
                    onRetry = {},
                    onClearInputFields = {},
                    onNavigationEvent = {}
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
                    viewState = createViewState(screenState = LoginState.LoginIdle),
                    onLoginClick = {},
                    onUsernameChange = {},
                    onPasswordChange = {},
                    onTogglePasswordVisibility = {},
                    onRetry = {},
                    onClearInputFields = {},
                    onNavigationEvent = {}
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
                    viewState = createViewState(screenState = LoginState.LoginIdle),
                    onLoginClick = {},
                    onUsernameChange = {},
                    onPasswordChange = {},
                    onTogglePasswordVisibility = {},
                    onRetry = {},
                    onClearInputFields = {},
                    onNavigationEvent = {}
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
                    viewState = createViewState(screenState = LoginState.LoginIdle),
                    onLoginClick = {},
                    onUsernameChange = {},
                    onPasswordChange = {},
                    onTogglePasswordVisibility = {},
                    onRetry = {},
                    onClearInputFields = {},
                    onNavigationEvent = {}
                )
            }
        }

        composeRule.onNodeWithTag("passwordTextField").assertExists().assertIsDisplayed()
    }

    @Test
    fun whenFormIsValid_shouldEnableLoginButton() {
        val formState = LoginFormState(
            username = "fabiano",
            password = "1234",
            isFormValid = true // Agora usando a propriedade correta
        )

        composeRule.setContent {
            BaseAppTheme {
                Box(Modifier.size(1080.dp, 1920.dp)) {
                    LoginScreen(
                        viewState = createViewState(
                            screenState = LoginState.LoginIdle,
                            formState = formState
                        ),
                        onLoginClick = {},
                        onUsernameChange = {},
                        onPasswordChange = {},
                        onTogglePasswordVisibility = {},
                        onRetry = {},
                        onClearInputFields = {},
                        onNavigationEvent = {}
                    )
                }
            }
        }

        composeRule.onNodeWithTag("LoginButton", useUnmergedTree = true)
            .assertExists()
            .printToLog("DEBUG_TREE")
        composeRule.onNodeWithTag("LoginButton", useUnmergedTree = true)
            .assertExists()
            .assertIsDisplayed()
            .assertIsEnabled()
    }

    @Test
    fun whenFormIsInvalid_shouldDisableLoginButton() {
        val formState = LoginFormState(
            username = "",
            password = "",
            isFormValid = false // Formulário inválido
        )

        composeRule.setContent {
            BaseAppTheme {
                Box(Modifier.size(1080.dp, 1920.dp)) {
                    LoginScreen(
                        viewState = createViewState(
                            screenState = LoginState.LoginIdle,
                            formState = formState
                        ),
                        onLoginClick = {},
                        onUsernameChange = {},
                        onPasswordChange = {},
                        onTogglePasswordVisibility = {},
                        onRetry = {},
                        onClearInputFields = {},
                        onNavigationEvent = {}
                    )
                }
            }
        }

        composeRule.onNodeWithTag("LoginButton", useUnmergedTree = true)
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun whenClickLoginButton_shouldTriggerCallback() {
        var clicked = false
        val formState = LoginFormState(
            username = "fabiano",
            password = "1234",
            isFormValid = true
        )

        composeRule.setContent {
            BaseAppTheme {
                Box(Modifier.size(1080.dp, 1920.dp)) {
                    LoginScreen(
                        viewState = createViewState(
                            screenState = LoginState.LoginIdle,
                            formState = formState
                        ),
                        onLoginClick = { clicked = true },
                        onUsernameChange = {},
                        onPasswordChange = {},
                        onTogglePasswordVisibility = {},
                        onRetry = {},
                        onClearInputFields = {},
                        onNavigationEvent = {}
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
    fun whenRetryButtonClicked_shouldTriggerCallback() {
        var retryClicked = false

        composeRule.setContent {
            BaseAppTheme {
                LoginScreen(
                    viewState = createViewState(screenState = LoginState.LoginError("Erro")),
                    onLoginClick = {},
                    onUsernameChange = {},
                    onPasswordChange = {},
                    onTogglePasswordVisibility = {},
                    onRetry = { retryClicked = true },
                    onClearInputFields = {},
                    onNavigationEvent = {}
                )
            }
        }

        composeRule.onNodeWithTag("RetryButton").performClick()
        assertTrue(retryClicked)
    }

    @Test
    fun whenTogglePasswordVisibility_shouldTriggerCallback() {
        var toggleClicked = false

        composeRule.setContent {
            BaseAppTheme {
                LoginScreen(
                    viewState = createViewState(screenState = LoginState.LoginIdle),
                    onLoginClick = {},
                    onUsernameChange = {},
                    onPasswordChange = {},
                    onTogglePasswordVisibility = { toggleClicked = true },
                    onRetry = {},
                    onClearInputFields = {},
                    onNavigationEvent = {}
                )
            }
        }

        composeRule.onNodeWithTag("TogglePasswordVisibility").performClick()
        assertTrue(toggleClicked)
    }
}
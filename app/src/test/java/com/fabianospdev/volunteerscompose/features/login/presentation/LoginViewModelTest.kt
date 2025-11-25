package com.fabianospdev.volunteerscompose.features.login.presentation

import app.cash.turbine.test
import com.fabianospdev.volunteerscompose.core.FakeRetryController
import com.fabianospdev.volunteerscompose.core.MainDispatcherRule
import com.fabianospdev.volunteerscompose.core.di.DispatcherProvider
import com.fabianospdev.volunteerscompose.core.helpers.coroutines.TestDispatcherProvider
import com.fabianospdev.volunteerscompose.core.helpers.retry.DefaultRetryController
import com.fabianospdev.volunteerscompose.features.login.domain.entities.LoginResponseEntity
import com.fabianospdev.volunteerscompose.features.login.domain.usecases.LoginUsecase
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginNavigationEvent
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.clearAllMocks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var loginUsecase: LoginUsecase
    private lateinit var retryController: FakeRetryController
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        dispatcherProvider = TestDispatcherProvider(mainRule.dispatcher)
        loginUsecase = mockk()
        retryController = FakeRetryController()

        viewModel = LoginViewModel(
            loginUsecase = loginUsecase,
            retryController = retryController,
            dispatcherProvider = dispatcherProvider
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `should emit Error when login fails`() = runTest {
        coEvery { loginUsecase.getLogin("user@test.com", "123456") } returns Result.failure(Exception("Login failed"))

        viewModel.onUsernameChange("user@test.com")
        viewModel.onPasswordChange("123456")
        viewModel.onLoginClick()
        advanceUntilIdle()

        val state = viewModel.viewState.value.screenState
        assertEquals(LoginState.LoginError("Login failed"), state)
    }

    @Test
    fun `should show validation error when fields are empty`() = runTest {
        viewModel.onUsernameChange("")
        viewModel.onPasswordChange("")
        viewModel.onLoginClick()

        val state = viewModel.viewState.value
        assertEquals("E-mail é obrigatório", state.formState.usernameError)
        assertEquals("Senha é obrigatória", state.formState.passwordError)
        assertFalse(state.formState.isFormValid)
    }

    @Test
    fun `should update username`() = runTest {
        viewModel.onUsernameChange("user@example.com")

        assertEquals("user@example.com", viewModel.viewState.value.formState.username)
    }

    @Test
    fun `should update password`() = runTest {
        viewModel.onPasswordChange("123456")

        assertEquals("123456", viewModel.viewState.value.formState.password)
    }

    @Test
    fun `should show validation error when only username is empty`() = runTest {
        viewModel.onUsernameChange("")
        viewModel.onPasswordChange("123456")
        viewModel.onLoginClick()

        val state = viewModel.viewState.value
        assertEquals("E-mail é obrigatório", state.formState.usernameError)
        assertEquals(null, state.formState.passwordError)
        assertFalse(state.formState.isFormValid)
    }

    @Test
    fun `should retry login when retry is called`() = runTest {
        coEvery { loginUsecase.getLogin(any(), any()) } returns Result.failure(Exception("network error"))

        viewModel.onUsernameChange("a@b.com")
        viewModel.onPasswordChange("123456")

        viewModel.onLoginClick()
        advanceUntilIdle()

        viewModel.onRetry()
        advanceUntilIdle()

        coVerify(exactly = 2) { loginUsecase.getLogin("a@b.com", "123456") }
    }

    @Test
    fun `should emit Loading then Success`() = runTest {
        val successResponse = LoginResponseEntity("1", "Pedro Santos", "a@b.com", "abc123")
        coEvery { loginUsecase.getLogin("a@b.com", "123456") } returns Result.success(successResponse)

        viewModel.onUsernameChange("a@b.com")
        viewModel.onPasswordChange("123456")

        viewModel.viewState.test {
            val initialState = awaitItem()
            assertEquals(LoginState.LoginIdle, initialState.screenState)

            viewModel.onLoginClick()

            awaitItem() // necessário pois a troca de estado sempre passa por idle

            val loadingState = awaitItem()
            assertEquals(LoginState.LoginLoading, loadingState.screenState)

            advanceUntilIdle() // Aguarda a conclusão da corrotina
            awaitItem()

            val successState = awaitItem()
            assertTrue(successState.screenState is LoginState.LoginSuccess)

            val loginSuccess = successState.screenState
            assertEquals(successResponse, loginSuccess.response)

            cancelAndConsumeRemainingEvents()
        }

        coVerify(exactly = 1) { loginUsecase.getLogin("a@b.com", "123456") }
    }

    @Test
    fun `should toggle password visibility`() = runTest {
        val initialVisibility = viewModel.viewState.value.formState.showPassword

        viewModel.onTogglePasswordVisibility()

        assertEquals(!initialVisibility, viewModel.viewState.value.formState.showPassword)

        viewModel.onTogglePasswordVisibility()

        assertEquals(initialVisibility, viewModel.viewState.value.formState.showPassword)
    }

    @Test
    fun `should clear input fields`() = runTest {
        viewModel.onUsernameChange("test@example.com")
        viewModel.onPasswordChange("password123")

        viewModel.clearInputFields()

        val state = viewModel.viewState.value.formState
        assertEquals("", state.username)
        assertEquals("", state.password)
        assertEquals(null, state.usernameError)
        assertEquals(null, state.passwordError)
        assertFalse(state.isFormValid)
    }

    @Test
    fun `should reset state`() = runTest {
        // Primeiro mude para um estado de erro
        coEvery { loginUsecase.getLogin(any(), any()) } returns Result.failure(Exception("error"))
        viewModel.onUsernameChange("test@example.com")
        viewModel.onPasswordChange("123456")
        viewModel.onLoginClick()
        advanceUntilIdle()

        // Verifique que está em estado de erro
        assertTrue(viewModel.viewState.value.screenState is LoginState.LoginError)

        // Reset e verifique que voltou para Idle
        viewModel.resetState()
        assertEquals(LoginState.LoginIdle, viewModel.viewState.value.screenState)
    }

    @Test
    fun `should emit navigation event when navigating to home`() = runTest {
        viewModel.navigationEvents.test {
            viewModel.onNavigateToHome()

            val event = awaitItem()
            assertEquals(LoginNavigationEvent.NavigateToHome, event)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `should emit navigation event when navigating to settings`() = runTest {
        viewModel.navigationEvents.test {
            viewModel.onNavigateToSettings()

            val event = awaitItem()
            assertEquals(LoginNavigationEvent.NavigateToSettings, event)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `should validate email format`() = runTest {
        viewModel.onUsernameChange("invalid-email")
        viewModel.onPasswordChange("123456")
        viewModel.onLoginClick()

        val state = viewModel.viewState.value
        assertEquals("E-mail inválido", state.formState.usernameError)
        assertFalse(state.formState.isFormValid)
    }

    @Test
    fun `should validate password length`() = runTest {
        viewModel.onUsernameChange("test@example.com")
        viewModel.onPasswordChange("123")
        viewModel.onLoginClick()

        val state = viewModel.viewState.value
        assertEquals("Senha deve ter pelo menos 6 caracteres", state.formState.passwordError)
        assertFalse(state.formState.isFormValid)
    }

    // Teste de cooldown REAL
    @Test
    fun `should unblock retry after cooldown`() = runTest {
        val retry = DefaultRetryController(
            maxRetries = 3,
            retryCooldownMillis = 30_000,
            autoReset = true,
            scope = this
        )

        val vm = LoginViewModel(
            loginUsecase,
            retry,
            dispatcherProvider
        )

        // aciona o limite
        retry.incrementRetryCount()
        retry.incrementRetryCount()
        retry.incrementRetryCount()

        assertFalse(retry.isRetryEnabled.value)

        // avança tempo
        advanceTimeBy(30_000)
        advanceUntilIdle()

        assertTrue(retry.isRetryEnabled.value)
    }
}
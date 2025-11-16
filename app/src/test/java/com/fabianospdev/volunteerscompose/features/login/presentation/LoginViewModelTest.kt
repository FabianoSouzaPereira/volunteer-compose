package com.fabianospdev.volunteerscompose.features.login.presentation

import app.cash.turbine.test
import com.fabianospdev.volunteerscompose.core.FakeRetryController
import com.fabianospdev.volunteerscompose.core.MainDispatcherRule
import com.fabianospdev.volunteerscompose.core.di.DispatcherProvider
import com.fabianospdev.volunteerscompose.core.helpers.coroutines.TestDispatcherProvider
import com.fabianospdev.volunteerscompose.core.helpers.retry.DefaultRetryController
import com.fabianospdev.volunteerscompose.features.login.domain.entities.LoginResponseEntity
import com.fabianospdev.volunteerscompose.features.login.domain.usecases.LoginUsecase
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

        assertEquals(LoginState.LoginError("Login failed"), viewModel.state.value)
    }

    @Test
    fun `should stay Idle when fields are empty`() = runTest {
        viewModel.onUsernameChange("")
        viewModel.onPasswordChange("")
        viewModel.onLoginClick()
        assertEquals(LoginState.LoginIdle, viewModel.state.value)
    }

    @Test
    fun `should update username`() {
        viewModel.onUsernameChange("user@example.com")
        assertEquals("user@example.com", viewModel.username.value)
    }

    @Test
    fun `should update password`() {
        viewModel.onPasswordChange("123456")
        assertEquals("123456", viewModel.password.value)
    }

    @Test
    fun `should stay Idle when only username is empty`() = runTest {
        viewModel.onUsernameChange("")
        viewModel.onPasswordChange("123456")
        viewModel.onLoginClick()
        assertEquals(LoginState.LoginIdle, viewModel.state.value)
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
        coEvery { loginUsecase.getLogin(any(), any()) } returns Result.success(
            LoginResponseEntity("1", "Pedro Santos", "a@b.com", "abc123")
        )

        viewModel.onUsernameChange("a@b.com")
        viewModel.onPasswordChange("123456")

        viewModel.state.test {
            awaitItem() // Idle

            viewModel.onLoginClick()

            assert(awaitItem() is LoginState.LoginLoading)

            advanceUntilIdle()

            assert(awaitItem() is LoginState.LoginSuccess)

            cancelAndConsumeRemainingEvents()
        }

        coVerify(exactly = 1) { loginUsecase.getLogin("a@b.com", "123456") }
    }

    // ------------------------------
    // Teste de cooldown REAL
    // ------------------------------
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

        assert(!retry.isRetryEnabled.value)

        // avança tempo
        advanceTimeBy(30_000)
        advanceUntilIdle()

        assert(retry.isRetryEnabled.value)
    }
}

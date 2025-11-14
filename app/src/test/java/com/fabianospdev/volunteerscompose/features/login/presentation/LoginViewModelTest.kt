package com.fabianospdev.volunteerscompose.features.login.presentation

import app.cash.turbine.test
import com.fabianospdev.volunteerscompose.core.MainDispatcherRule
import com.fabianospdev.volunteerscompose.core.helpers.coroutines.TestDispatcherProvider
import com.fabianospdev.volunteerscompose.core.helpers.retry.RetryController
import com.fabianospdev.volunteerscompose.features.login.domain.entities.LoginResponseEntity
import com.fabianospdev.volunteerscompose.features.login.domain.usecases.LoginUsecase
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelMockKTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val testDispatcher = StandardTestDispatcher()
    private val dispatcherProvider = TestDispatcherProvider(testDispatcher)

    private val dispatcher = StandardTestDispatcher()
    private lateinit var viewModel: LoginViewModel
    private lateinit var usecase: LoginUsecase
    private lateinit var retryController: RetryController

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        usecase = mockk()
        retryController = mockk()

        // Mock default behavior
        every { retryController.isRetryEnabled } returns MutableStateFlow(true)

        val testDispatcherProvider = TestDispatcherProvider(mainDispatcherRule.dispatcher)
        viewModel = LoginViewModel(usecase, retryController, testDispatcherProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `should emit Success when login succeeds`() = runTest {
        val expectedResponse = LoginResponseEntity("1", "Mock User", "email@email.com", "token")
        coEvery { usecase.getLogin("user@test.com", "123456") } returns Result.success(expectedResponse)

        viewModel.onUsernameChange("user@test.com")
        viewModel.onPasswordChange("123456")
        viewModel.onLoginClick()
        advanceUntilIdle()

        assertEquals(LoginState.LoginSuccess(expectedResponse), viewModel.state.value)
    }

    @Test
    fun `should emit Error when login fails`() = runTest {
        coEvery { usecase.getLogin("user@test.com", "123456") } returns Result.failure(Exception("Login failed"))

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
    fun `should update username when onUsernameChange is called`() {
        viewModel.onUsernameChange("user@example.com")
        assertEquals("user@example.com", viewModel.username.value)
    }

    @Test
    fun `should update password when onPasswordChange is called`() {
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
        coEvery { usecase.getLogin(any(), any()) } returns Result.failure(Exception("error"))

        viewModel.onUsernameChange("a@b.com")
        viewModel.onPasswordChange("123")
        viewModel.onLoginClick()
        advanceUntilIdle()

        coEvery { usecase.getLogin(any(), any()) } returns Result.success(
            LoginResponseEntity("1", "User", "a@b.com", "token")
        )

        viewModel.onRetry()
        advanceUntilIdle() // <-- Agora vai esperar o launch da viewModel

        assertEquals(
            LoginState.LoginSuccess(LoginResponseEntity("1", "User", "a@b.com", "token")),
            viewModel.state.value
        )

        confirmVerified(usecase)
    }


    @Test
    fun `should emit Loading then Success`() = runTest {
        val expectedResponse = LoginResponseEntity("1", "User", "a@b.com", "token")

        coEvery { usecase.getLogin(any(), any()) } returns Result.success(expectedResponse)

        val viewModel = LoginViewModel(usecase, retryController, dispatcherProvider)
        viewModel.onUsernameChange("a@b.com")
        viewModel.onPasswordChange("123")

        viewModel.state.test {
            assertEquals(LoginState.LoginIdle, awaitItem())

            viewModel.onLoginClick()
            advanceUntilIdle()

            assertEquals(LoginState.LoginLoading, awaitItem())
            assertEquals(LoginState.LoginSuccess(expectedResponse), awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should unblock retry after 30 seconds`() = runTest {
        viewModel.startRetryCooldown()
        advanceTimeBy(30000)
        assertFalse(viewModel.showRetryLimitReached.value)
    }
}

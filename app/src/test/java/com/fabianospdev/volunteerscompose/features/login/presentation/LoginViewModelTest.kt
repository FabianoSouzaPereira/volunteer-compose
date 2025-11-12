package com.fabianospdev.volunteerscompose.features.login.presentation

import com.fabianospdev.volunteerscompose.core.helpers.retry.RetryController
import com.fabianospdev.volunteerscompose.features.login.domain.entities.LoginResponseEntity
import com.fabianospdev.volunteerscompose.features.login.domain.usecases.LoginUsecase
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginState
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
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnit
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelMockitoTest {

    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    private val dispatcher = StandardTestDispatcher()
    private lateinit var viewModel: LoginViewModel

    @Mock lateinit var usecase: LoginUsecase
    @Mock lateinit var retryController: RetryController

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        usecase = mock(LoginUsecase::class.java)
        retryController = mock(RetryController::class.java)

        // Precisa simular retry habilitado
        whenever(retryController.isRetryEnabled).thenReturn(MutableStateFlow(true))

        viewModel = LoginViewModel(usecase, retryController)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should emit Success when login succeeds`() = runTest {
        // Arrange
        val expectedResponse = LoginResponseEntity("1", "Mock User", "email@email.com", "token")
        whenever(usecase.getLogin("user@test.com", "123456"))
            .thenReturn(Result.success(expectedResponse))

        // Act
        viewModel.onUsernameChange("user@test.com")
        viewModel.onPasswordChange("123456")
        viewModel.onLoginClick()
        advanceUntilIdle()

        // Assert
        assertEquals(LoginState.LoginSuccess(expectedResponse), viewModel.state.value)
    }

    @Test
    fun `should emit Error when login fails`() = runTest {
        // Arrange
        whenever(usecase.getLogin("user@test.com", "123456"))
            .thenReturn(Result.failure(Exception("Login failed")))

        // Act
        viewModel.onUsernameChange("user@test.com")
        viewModel.onPasswordChange("123456")
        viewModel.onLoginClick()
        advanceUntilIdle()

        // Assert
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
        // Simula falha anterior
        whenever(usecase.getLogin(anyString(), anyString()))
            .thenReturn(Result.failure(Exception("error")))

        viewModel.onUsernameChange("a@b.com")
        viewModel.onPasswordChange("123")
        viewModel.onLoginClick()
        advanceUntilIdle()

        // Muda comportamento para sucesso
        whenever(usecase.getLogin(anyString(), anyString()))
            .thenReturn(Result.success(LoginResponseEntity("1", "User", "a@b.com", "token")))

        viewModel.onRetry()
        advanceUntilIdle()

        assertEquals(LoginState.LoginSuccess(LoginResponseEntity("1", "User", "a@b.com", "token")), viewModel.state.value)
    }

    @Test
    fun `should emit Loading before Success`() = runTest {
        val expectedResponse = LoginResponseEntity("1", "User", "a@b.com", "token")

        whenever(usecase.getLogin(anyString(), anyString()))
            .thenAnswer {
                runTest {
                    advanceTimeBy(50)
                }
                Result.success(expectedResponse)
            }

        viewModel.onUsernameChange("a@b.com")
        viewModel.onPasswordChange("123")
        viewModel.onLoginClick()

        // Verifica estado intermediário
        assertEquals(LoginState.LoginLoading, viewModel.state.value)

        advanceUntilIdle()
        assertEquals(LoginState.LoginSuccess(expectedResponse), viewModel.state.value)
    }

    @Test
    fun `should unblock retry after 30 seconds`() = runTest {
        viewModel.startRetryCooldown()

        // Avança o tempo virtual de 30 segundos
        advanceTimeBy(30000)

        assertFalse(viewModel.showRetryLimitReached.value)
    }
}

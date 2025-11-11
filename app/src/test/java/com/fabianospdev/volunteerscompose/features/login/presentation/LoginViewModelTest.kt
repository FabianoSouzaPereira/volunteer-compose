package com.fabianospdev.volunteerscompose.features.login.presentation

import com.fabianospdev.volunteerscompose.core.helpers.retry.RetryController
import com.fabianospdev.volunteerscompose.features.login.domain.entities.LoginResponseEntity
import com.fabianospdev.volunteerscompose.features.login.domain.repositories.LoginRepository
import com.fabianospdev.volunteerscompose.features.login.domain.usecases.LoginUsecase
import com.fabianospdev.volunteerscompose.features.login.presentation.states.LoginState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
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

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelMockitoTest {

    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    private val dispatcher = StandardTestDispatcher()
    private lateinit var viewModel: LoginViewModel

    @Mock lateinit var repository: LoginRepository
    @Mock lateinit var usecase: LoginUsecase
    @Mock lateinit var retryController: RetryController

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        usecase = mock(LoginUsecase::class.java)
        retryController = mock(RetryController::class.java)
        viewModel = LoginViewModel(usecase, retryController)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should emit Success when login succeeds`() = runTest {
        val expectedResponse = LoginResponseEntity("1", "Mock User", "email@email.com", "token")

        whenever(usecase.getLogin(anyString(), anyString()))
            .thenReturn(Result.success(expectedResponse))

        viewModel.onLoginClick()
        advanceUntilIdle()

        assertEquals(LoginState.LoginSuccess(expectedResponse), viewModel.state.value)
    }

    @Test
    fun `should emit Error when login fails`() = runTest {
        whenever(usecase.getLogin(anyString(), anyString()))
            .thenReturn(Result.failure(Exception("Login failed")))

        viewModel.onLoginClick()
        advanceUntilIdle()

        assertEquals(LoginState.LoginError("Login failed"), viewModel.state.value)
    }
}

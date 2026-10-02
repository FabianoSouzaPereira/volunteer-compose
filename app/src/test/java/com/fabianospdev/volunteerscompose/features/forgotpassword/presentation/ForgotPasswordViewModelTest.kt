package com.fabianospdev.volunteerscompose.features.forgotpassword.presentation

import com.fabianospdev.volunteerscompose.core.MainDispatcherRule
import com.fabianospdev.volunteerscompose.core.helpers.coroutines.TestDispatcherProvider
import com.fabianospdev.volunteerscompose.features.forgotpassword.domain.entities.ForgotPasswordResponseEntity
import com.fabianospdev.volunteerscompose.features.forgotpassword.domain.usecases.ForgotPasswordUseCase
import com.fabianospdev.volunteerscompose.features.forgotpassword.presentation.states.ForgotPasswordState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ForgotPasswordViewModelTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    private val useCase: ForgotPasswordUseCase = mockk()

    private fun viewModel() = ForgotPasswordViewModel(
        forgotPasswordUseCase = useCase,
        dispatcherProvider = TestDispatcherProvider(mainRule.dispatcher)
    )

    @Test
    fun `should keep idle and show email error when email is blank`() = runTest {
        val viewModel = viewModel()

        viewModel.onSubmit()

        assertTrue(viewModel.viewState.value.screenState is ForgotPasswordState.ForgotPasswordIdle)
        assertEquals("E-mail é obrigatório", viewModel.viewState.value.formState.emailError)
        coVerify(exactly = 0) { useCase.requestPasswordReset(any()) }
    }

    @Test
    fun `should emit success when reset is accepted`() = runTest {
        coEvery { useCase.requestPasswordReset("ana@email.com") } returns Result.success(
            ForgotPasswordResponseEntity("Enviamos as instruções de redefinição para ana@email.com")
        )
        val viewModel = viewModel()

        viewModel.onEmailChange("ana@email.com")
        viewModel.onSubmit()
        advanceUntilIdle()

        val state = viewModel.viewState.value.screenState
            as ForgotPasswordState.ForgotPasswordSuccess
        assertEquals(
            "Enviamos as instruções de redefinição para ana@email.com",
            state.response.message
        )
    }
}

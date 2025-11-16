package com.fabianospdev.volunteerscompose.features.login.domain.usecases

import com.fabianospdev.volunteerscompose.features.login.domain.entities.LoginResponseEntity
import com.fabianospdev.volunteerscompose.features.login.domain.repositories.LoginRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LoginUsecaseImplTest {

    private val repository: LoginRepository = mockk()
    private val usecase = LoginUsecaseImpl(repository)

    @Test
    fun `should return success when repository returns success`() = runTest {
        val response = LoginResponseEntity("1", "Fabiano", "email@test.com", "token123")

        coEvery { repository.getLogin(any(), any()) } returns Result.success(response)

        val result = usecase.getLogin("a@a.com", "123")

        assertTrue(result.isSuccess)
        assertEquals(response, result.getOrNull())
    }

    @Test
    fun `should return failure when repository returns failure`() = runTest {
        val error = RuntimeException("fail")

        coEvery { repository.getLogin(any(), any()) } returns Result.failure(error)

        val result = usecase.getLogin("a@a.com", "123")

        assertTrue(result.isFailure)
    }
}

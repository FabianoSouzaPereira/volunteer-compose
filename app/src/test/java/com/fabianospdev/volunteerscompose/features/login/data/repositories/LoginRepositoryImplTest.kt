package com.fabianospdev.volunteerscompose.features.login.data.repositories

import com.fabianospdev.volunteerscompose.features.login.data.models.LoginResponseModel
import com.fabianospdev.volunteerscompose.features.login.domain.datasources.LoginDatasource
import com.fabianospdev.volunteerscompose.features.login.domain.entities.LoginResponseEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LoginRepositoryImplTest {

    private val datasource: LoginDatasource = mockk()
    private val repository = LoginRepositoryImpl(datasource)

    @Test
    fun `should convert model to entity on success`() = runTest {
        val model = LoginResponseModel("1", "Fabiano", "email@test.com", "token123")

        coEvery { datasource.getLogin(any(), any()) } returns Result.success(model)

        val result = repository.getLogin("a@a.com", "123")

        assertTrue(result.isSuccess)

        val entity = result.getOrNull()
        assertEquals(LoginResponseEntity("1", "Fabiano", "email@test.com", "token123"), entity)
    }

    @Test
    fun `should return failure when datasource throws`() = runTest {
        coEvery {
            datasource.getLogin(
                any(),
                any()
            )
        } returns Result.failure(RuntimeException("error"))

        val result = repository.getLogin("a@a.com", "123")

        assertTrue(result.isFailure)
    }
}
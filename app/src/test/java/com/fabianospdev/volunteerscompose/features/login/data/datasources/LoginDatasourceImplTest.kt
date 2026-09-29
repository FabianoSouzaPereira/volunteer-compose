package com.fabianospdev.volunteerscompose.features.login.data.datasources

import com.fabianospdev.volunteerscompose.core.helpers.exceptions.NetworkException
import com.fabianospdev.volunteerscompose.features.login.data.models.LoginResponseModel
import com.fabianospdev.volunteerscompose.features.login.data.remote.LoginApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import java.net.UnknownHostException
import kotlin.test.Test
import kotlin.test.assertTrue

class LoginDatasourceImplTest {

    private val api: LoginApiService = mockk()
    private val datasource = LoginDatasourceImpl(api)

    @Test
    fun `should return success when api returns success`() = runTest {
        val model = LoginResponseModel("1", "Fabiano", "email@test.com", "token123")

        coEvery { api.login(any()) } returns model

        val result = datasource.getLogin("a@a.com", "123")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `should return typed failure when api throws`() = runTest {
        coEvery { api.login(any()) } throws UnknownHostException("network down")

        val result = datasource.getLogin("a@a.com", "123")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is NetworkException)
    }
}

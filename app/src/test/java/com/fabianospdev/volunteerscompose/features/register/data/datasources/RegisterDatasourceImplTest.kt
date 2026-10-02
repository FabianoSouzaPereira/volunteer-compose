package com.fabianospdev.volunteerscompose.features.register.data.datasources

import com.fabianospdev.volunteerscompose.core.helpers.exceptions.ValidationException
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RegisterDatasourceImplTest {

    private val datasource = RegisterDatasourceImpl()

    @Test
    fun `should store the account when email is new`() = runTest {
        val result = datasource.register("Ana", "Ana@Email.com", "123456")

        assertTrue(result.isSuccess)
        assertEquals("ana@email.com", result.getOrNull()?.email)
        assertEquals("Ana", result.getOrNull()?.name)
    }

    @Test
    fun `should return validation failure when email is already registered`() = runTest {
        datasource.register("Ana", "ana@email.com", "123456")

        val result = datasource.register("Ana", "ANA@email.com", "123456")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is ValidationException)
        assertEquals("E-mail já cadastrado", result.exceptionOrNull()?.message)
    }
}

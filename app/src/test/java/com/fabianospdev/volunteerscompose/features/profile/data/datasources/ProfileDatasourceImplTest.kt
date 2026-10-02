package com.fabianospdev.volunteerscompose.features.profile.data.datasources

import com.fabianospdev.volunteerscompose.core.helpers.exceptions.ValidationException
import com.fabianospdev.volunteerscompose.features.profile.data.models.ProfileModel
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProfileDatasourceImplTest {

    private val datasource = ProfileDatasourceImpl()

    @Test
    fun `should return the stored profile`() = runTest {
        val result = datasource.getProfile()

        assertTrue(result.isSuccess)
        assertEquals("usuario@email.com", result.getOrNull()?.email)
    }

    @Test
    fun `should keep the saved profile`() = runTest {
        val saved = datasource.saveProfile(
            ProfileModel(name = "Ana", email = "ana@email.com", phone = "11999999999")
        )

        assertTrue(saved.isSuccess)
        assertEquals("Ana", datasource.getProfile().getOrNull()?.name)
        assertEquals("11999999999", datasource.getProfile().getOrNull()?.phone)
    }

    @Test
    fun `should reject a blank name`() = runTest {
        val result = datasource.saveProfile(
            ProfileModel(name = "  ", email = "ana@email.com", phone = "")
        )

        assertTrue(result.exceptionOrNull() is ValidationException)
    }
}

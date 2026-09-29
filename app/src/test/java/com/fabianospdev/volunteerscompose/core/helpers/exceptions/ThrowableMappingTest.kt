package com.fabianospdev.volunteerscompose.core.helpers.exceptions

import org.junit.Test
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.test.assertTrue
import retrofit2.HttpException
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class ThrowableMappingTest {

    @Test
    fun `unknown host becomes network exception`() {
        val mapped = UnknownHostException("dns").toRequestException()
        assertTrue(mapped is NetworkException)
    }

    @Test
    fun `socket timeout becomes timeout exception`() {
        val mapped = SocketTimeoutException("slow").toRequestException()
        assertTrue(mapped is TimeoutException)
    }

    @Test
    fun `http 401 becomes unauthorized`() {
        val response = Response.error<Unit>(
            401,
            "unauthorized".toResponseBody("text/plain".toMediaType())
        )
        val mapped = HttpException(response).toRequestException()
        assertTrue(mapped is UnauthorizedException)
    }

    @Test
    fun `already typed exception is kept`() {
        val original = ValidationException("invalid")
        val mapped = original.toRequestException()
        assertTrue(mapped === original)
    }
}

package com.fabianospdev.volunteerscompose.core.helpers.exceptions

import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException as JavaTimeoutException

fun Throwable.toRequestException(): RequestException {
    if (this is RequestException) return this
    (cause as? RequestException)?.let { return it }

    return when (this) {
        is SocketTimeoutException, is JavaTimeoutException ->
            TimeoutException(message ?: "Request timed out", this)

        is UnknownHostException, is ConnectException ->
            NetworkException(message ?: "Network error", this)

        is HttpException -> {
            val msg = message ?: "HTTP ${code()}"
            when (code()) {
                400 -> BadRequestException(msg, this)
                401 -> UnauthorizedException(msg, this)
                403 -> ForbiddenException(msg, this)
                404 -> ItemNotFoundException(msg, this)
                409 -> ConflictException(msg, this)
                in 500..599 -> ServerException(msg, this)
                else -> ServerException(msg, this)
            }
        }

        is IOException ->
            NetworkException(message ?: "Network error", this)

        else -> RequestException(message ?: "Unknown error", this)
    }
}

fun Throwable.errorMessage(): String = message ?: "Erro desconhecido"

package com.fabianospdev.volunteerscompose.core.helpers.retry

import kotlinx.coroutines.flow.StateFlow

interface RetryController {
    val isRetryEnabled: StateFlow<Boolean>
    val isRetryLimitReached: StateFlow<Boolean>

    val retryCooldownMillis: Long
        get() = 30_000L

    val autoReset: Boolean
        get() = true

    fun incrementRetryCount()
    fun resetRetryCount()
    fun resetRetryLimitNotification()
}
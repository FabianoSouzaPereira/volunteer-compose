package com.fabianospdev.volunteerscompose.core.helpers.retry

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultRetryController(
    private val maxRetries: Int = 3,
    override val retryCooldownMillis: Long = 30_000L,
    override val autoReset: Boolean = true,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : RetryController {
    private val _isRetryEnabled = MutableStateFlow(true)
    override val isRetryEnabled: StateFlow<Boolean> = _isRetryEnabled

    private val _isRetryLimitReached = MutableStateFlow(false)
    override val isRetryLimitReached: StateFlow<Boolean> get() = _isRetryLimitReached

    private var retryCount = 0

    override fun incrementRetryCount() {
        retryCount++
        if (retryCount >= maxRetries) {
            _isRetryEnabled.value = false
            _isRetryLimitReached.value = true
            if (autoReset) startCooldownTimer()
        }
    }

    private fun startCooldownTimer() {
        scope.launch {
            delay(retryCooldownMillis)
            resetRetryCount()
        }
    }

    override fun resetRetryCount() {
        retryCount = 0
        _isRetryEnabled.value = true
        _isRetryLimitReached.value = false
    }

    override fun resetRetryLimitNotification() {
        retryCount = 0
        _isRetryLimitReached.value = true
    }
}

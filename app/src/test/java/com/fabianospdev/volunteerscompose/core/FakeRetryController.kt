package com.fabianospdev.volunteerscompose.core
/**
 * A lightweight, fully controllable test double for [RetryController].
 *
 * Why this exists:
 * - The real DefaultRetryController depends on asynchronous timers and coroutine scopes,
 *   which makes unit testing difficult and non-deterministic.
 * - In tests, we need immediate and predictable behavior when simulating retry limits,
 *   cooldowns, and state changes.
 *
 * What this fake provides:
 * - Manual control over retry enable/disable state.
 * - Manual triggering of "retry limit reached" without waiting for real timers.
 * - No internal coroutines, delays, or background work — ensuring stable, deterministic tests.
 *
 * Usage:
 * - Use this fake whenever you need to simulate retry logic in ViewModel or use-case tests
 *   without relying on timers or Dispatchers.
 * - Particularly useful for Turbine-based StateFlow tests, where timing must be explicit.
 *
 * This class ensures your business logic can be tested independently of time-based logic
 * while still validating all retry-related behaviors.
 */

import com.fabianospdev.volunteerscompose.core.helpers.retry.RetryController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.TestScope

class FakeRetryController(
    var maxRetries: Int = 3,
    override val retryCooldownMillis: Long = 30_000L,
    override val autoReset: Boolean = false, // no auto reset em testes
    private val scope: CoroutineScope = TestScope()
) : RetryController {

    private val _isRetryEnabled = MutableStateFlow(true)
    override val isRetryEnabled: StateFlow<Boolean> = _isRetryEnabled

    private val _isRetryLimitReached = MutableStateFlow(false)
    override val isRetryLimitReached: StateFlow<Boolean> = _isRetryLimitReached

    var retryCount = 0

    override fun incrementRetryCount() {
        retryCount++
        if (retryCount >= maxRetries) {
            _isRetryEnabled.value = false
            _isRetryLimitReached.value = true
        }
    }

    override fun resetRetryCount() {
        retryCount = 0
        _isRetryEnabled.value = true
        _isRetryLimitReached.value = false
    }

    override fun resetRetryLimitNotification() {
        _isRetryLimitReached.value = false
    }
}

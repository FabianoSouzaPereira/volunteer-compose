package com.fabianospdev.volunteerscompose.core

import com.fabianospdev.volunteerscompose.core.di.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher

/**
 * A test-friendly implementation of [DispatcherProvider] that routes all coroutine dispatchers
 * (main, io, default, unconfined) to a single [TestDispatcher].
 *
 * Why this exists:
 * - In production, coroutines run on different dispatchers (Main, IO, Default), which makes
 *   behavior asynchronous and harder to control during tests.
 * - Unit tests must run deterministically; therefore, all coroutine execution is redirected
 *   to a controllable TestDispatcher (e.g., StandardTestDispatcher).
 * - This allows tests to explicitly advance coroutine time using `advanceUntilIdle()` or
 *   `advanceTimeBy()`, ensuring stable, reproducible results.
 *
 * Benefits:
 * - Removes flakiness caused by background threads.
 * - Ensures ViewModel and use-case coroutines run in a fully controlled environment.
 * - Makes Turbine, MockK, and coroutine timing tests far more predictable.
 *
 * Use this class whenever your ViewModel or business logic depends on DispatcherProvider
 * and you want complete control over coroutine execution during tests.
 */
class TestDispatcherProvider(
    val dispatcher: TestDispatcher = StandardTestDispatcher()
) : DispatcherProvider {

    override val main: CoroutineDispatcher = dispatcher
    override val io: CoroutineDispatcher = dispatcher
    override val default: CoroutineDispatcher = dispatcher
    override val unconfined: CoroutineDispatcher = dispatcher
}

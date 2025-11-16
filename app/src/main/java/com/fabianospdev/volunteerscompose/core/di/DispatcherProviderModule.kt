package com.fabianospdev.volunteerscompose.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * Abstraction over coroutine dispatchers used throughout the application.
 *
 * Why this interface exists:
 * - Avoids coupling ViewModels and use-cases to Dispatchers directly.
 * - Makes coroutine execution testable by allowing replacement with a TestDispatcherProvider.
 * - Ensures deterministic coroutine behavior in unit tests.
 *
 * Production:
 * - DefaultDispatcherProvider supplies actual Android Dispatchers.
 *
 * Tests:
 * - A custom TestDispatcherProvider can inject a controllable TestDispatcher,
 *   removing the need for real background threads and making tests stable.
 *   author: Fabiano de Souza Pereira
 */
interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}

/** Production implementation of DispatcherProvider using real Dispatchers. */
class DefaultDispatcherProvider : DispatcherProvider {
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
    override val unconfined = Dispatchers.Unconfined
}

@Module
@InstallIn(SingletonComponent::class)
object DispatcherProviderModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
}

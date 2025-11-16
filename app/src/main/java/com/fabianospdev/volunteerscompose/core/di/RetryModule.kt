package com.fabianospdev.volunteerscompose.core.di

import com.fabianospdev.volunteerscompose.core.helpers.retry.DefaultRetryController
import com.fabianospdev.volunteerscompose.core.helpers.retry.RetryController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(SingletonComponent::class)
object RetryModule {

    @Provides
    @Singleton
    fun provideRetryController(
        @ApplicationScope appScope: CoroutineScope
    ): RetryController {
        return DefaultRetryController(
            maxRetries = 3,
            retryCooldownMillis = 30_000L,
            autoReset = true,
            scope = appScope
        )
    }
}
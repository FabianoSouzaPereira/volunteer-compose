package com.fabianospdev.volunteerscompose.core.di

import com.fabianospdev.volunteerscompose.core.helpers.EncryptedTokenManager
import com.fabianospdev.volunteerscompose.core.helpers.TokenManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TokenModule {

    @Binds
    @Singleton
    abstract fun bindTokenManager(impl: EncryptedTokenManager): TokenManager
}

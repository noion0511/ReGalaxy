package com.ssafy.phonesin.di

import com.ssafy.phonesin.network.ApiServiceHolder
import com.ssafy.phonesin.network.TokenAuthenticator
import com.ssafy.phonesin.network.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TokenAuthenticatorModule {

    @Provides
    @Singleton
    fun provideApiServiceHolder(): ApiServiceHolder {
        return ApiServiceHolder()
    }

    @Provides
    @Singleton
    fun provideTokenAuthenticator(tokenManager: TokenManager): TokenAuthenticator {
        return TokenAuthenticator(tokenManager)
    }

    @Provides
    @Singleton
    fun provideTokenManager(apiServiceHolder: ApiServiceHolder): TokenManager {
        return TokenManager(apiServiceHolder)
    }
}

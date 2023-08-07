package com.ssafy.phonesin.di

import com.ssafy.phonesin.network.TokenAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object TokenAuthenticatorModule {

    @Provides
    fun provideTokenAuthenticator(retrofit: Retrofit): TokenAuthenticator {
        return TokenAuthenticator(retrofit)
    }
}

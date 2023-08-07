package com.ssafy.phonesin.di

import com.ssafy.phonesin.network.TokenAuthenticator
import com.ssafy.phonesin.repository.login.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TokenAuthenticatorModule {

    @Provides
    fun provideTokenAuthenticator(repository: LoginRepository): TokenAuthenticator {
        return TokenAuthenticator(repository)
    }
}

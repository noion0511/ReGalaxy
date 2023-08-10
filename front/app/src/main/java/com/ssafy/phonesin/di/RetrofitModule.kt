package com.ssafy.phonesin.di

import com.ssafy.phonesin.repository.ytwok.Y2KRepository
import com.ssafy.phonesin.repository.ytwok.Y2KRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsY2KRepository(
        repositoryImpl: Y2KRepositoryImpl
    ): Y2KRepository
}
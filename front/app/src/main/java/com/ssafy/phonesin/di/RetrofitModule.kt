package com.ssafy.phonesin.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

//    @Binds
//    abstract fun bindsUserRepository(
//        repositoryImpl: UserRepositoryImpl
//    ): UserRepository
}
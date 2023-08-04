package com.ssafy.phonesin.di


import com.ssafy.phonesin.repository.donation.DonationRepository
import com.ssafy.phonesin.repository.donation.DonationRepositoryImpl
import com.ssafy.phonesin.repository.returnmobile.ReturnRepository
import com.ssafy.phonesin.repository.returnmobile.ReturnRepositoryImpl
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

    @Binds
    abstract fun bindsReturnRepository(
        repositoryImpl: ReturnRepositoryImpl
    ): ReturnRepository

    abstract fun bindsDonationRepository(
        repositoryImpl: DonationRepositoryImpl
    ): DonationRepository

}
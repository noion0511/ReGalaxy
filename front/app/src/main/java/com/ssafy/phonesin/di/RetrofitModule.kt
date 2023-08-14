package com.ssafy.phonesin.di

import com.ssafy.phonesin.repository.login.LoginRepository
import com.ssafy.phonesin.repository.login.LoginRepositoryImpl
import com.ssafy.phonesin.repository.user.UserRepository
import com.ssafy.phonesin.repository.user.UserRepositoryImpl
import com.ssafy.phonesin.repository.address.AddressRepository
import com.ssafy.phonesin.repository.address.AddressRepositoryImpl
import com.ssafy.phonesin.repository.donation.DonationRepository
import com.ssafy.phonesin.repository.donation.DonationRepositoryImpl
import com.ssafy.phonesin.repository.hygrometer.HygrometerRepository
import com.ssafy.phonesin.repository.hygrometer.HygrometerRepositoryImpl
import com.ssafy.phonesin.repository.rental.RentalRepository
import com.ssafy.phonesin.repository.rental.RentalRepositoryImpl
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

    @Binds
    abstract fun bindsDonationRepository(
        repositoryImpl: DonationRepositoryImpl
    ): DonationRepository

    @Binds
    abstract fun bindsRentalRepository(
        repositoryImpl: RentalRepositoryImpl
    ): RentalRepository

    @Binds
    abstract fun bindsAddressRepository(
        repositoryImpl: AddressRepositoryImpl
    ): AddressRepository

    @Binds
    abstract fun bindsLoginRepository(
        repositoryImpl: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    abstract fun bindsUserRepository(
        repositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindsHygrometerRepository(
        repositoryImpl: HygrometerRepositoryImpl
    ) : HygrometerRepository
}
package com.ssafy.phonesin.ui.mypage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.phonesin.model.Address
import com.ssafy.phonesin.model.UserRental
import com.ssafy.phonesin.model.UserReturn
import com.ssafy.phonesin.model.User
import com.ssafy.phonesin.model.UserDonation
import com.ssafy.phonesin.model.UserModify
import com.ssafy.phonesin.network.NetworkResponse
import com.ssafy.phonesin.repository.address.AddressRepository
import com.ssafy.phonesin.repository.user.UserRepository
import com.ssafy.phonesin.ui.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val addressRepository: AddressRepository,
    private val userRepository: UserRepository,
) : BaseViewModel() {

    private val _address = MutableLiveData<List<Address>>()
    val addressList: MutableLiveData<List<Address>>
        get() = _address

    private val _user = MutableLiveData<User>()
    val user: MutableLiveData<User>
        get() = _user

    private val _donation = MutableLiveData<List<UserDonation>>()
    val myDonationList: MutableLiveData<List<UserDonation>>
        get() = _donation

    private val _rental = MutableLiveData<List<UserRental>>()
    val myRentalList: MutableLiveData<List<UserRental>>
        get() = _rental

    private val _return = MutableLiveData<List<UserReturn>>()
    val myReturnList: MutableLiveData<List<UserReturn>>
        get() = _return


    fun postAddress(address: String) {
        viewModelScope.launch {
            addressRepository.postAddress(address)
        }
    }

    fun getAddress() {
        viewModelScope.launch {
            val response = addressRepository.getAddress()
            when (response) {
                is NetworkResponse.Success -> {
                    _address.value = response.body
                }
                else -> {}
            }
        }
    }

    fun removeAddress(addressId: Int) {
        viewModelScope.launch {
            addressRepository.removeAddress(addressId)
            getAddress()
        }
    }

    fun withdrawal() {
        viewModelScope.launch {
            userRepository.withdrawl()
        }
    }

    fun getUserInfo() {
        viewModelScope.launch {
            val response = userRepository.getUserInfo()
            if (response.status == 200) {
                _user.value = response.member
            }
        }
    }

    fun updateUserInfo(newInfo: UserModify) {
        viewModelScope.launch {
            val response = userRepository.updateUserInfo(newInfo)
            getUserInfo()
        }
    }

    fun getUserDonationList() {
        viewModelScope.launch {
            val response = userRepository.getUserDonationList()
            when(response) {
                is NetworkResponse.Success -> {
                    _donation.value = response.body.donation
                }
                else -> {}
            }
        }
    }

    fun cancelUserDonation(donationId: Int) {
        viewModelScope.launch {
            userRepository.cancelUserDonation(donationId)
            getUserDonationList()
        }
    }

    fun getUserRental() {
        viewModelScope.launch {
            val response = userRepository.getUserRentalList()
            when(response) {
                is NetworkResponse.Success -> {
                    _rental.value = response.body.rentalList
                }
                else -> {}
            }
        }
    }

    fun cancelUserRental(rentalId: Int) {
        viewModelScope.launch {
            userRepository.cancelUserRental(rentalId)
            getUserRental()
        }
    }

    fun getUserReturn() {
        viewModelScope.launch {
            val response = userRepository.getUserReturnList()
            when(response) {
                is NetworkResponse.Success -> {
                    _return.value = response.body.returnList
                }
                else -> {}
            }
        }
    }

    fun cancelUserReturn(returnId: Int) {
        viewModelScope.launch {
            userRepository.cancelUserReturn(returnId)
            getUserReturn()
        }
    }
}
package com.ssafy.phonesin.ui.mobile.donatemobile

import androidx.lifecycle.viewModelScope
import com.ssafy.phonesin.model.Donation
import com.ssafy.phonesin.repository.donation.DonationRepository
import com.ssafy.phonesin.ui.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DonateViewModel @Inject constructor(
    private val repository: DonationRepository
) : BaseViewModel() {
    private val _donation = Donation("2023-08-03", "string", "string", 0)
    val donation: Donation
        get() = _donation

    fun uploadDonation() {
        viewModelScope.launch {
            //  repository.uploadDonation(_donation)
        }
    }

    fun setDateDonate(date: String) {
        _donation.donationDeliveryDate = date
    }

    fun setLocationDonate(location: String) {
        _donation.donationDeliveryLocation = location
    }

    fun setTypeDonate(type: String) {
        _donation.donationDeliveryLocationType = type
    }

}
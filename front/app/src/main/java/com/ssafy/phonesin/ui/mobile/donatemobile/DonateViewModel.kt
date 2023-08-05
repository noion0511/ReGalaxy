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
    private val _homeMusicList = Donation("", "2023-08-03", "string", "string", 0, 0, 8)
    val homeMusicList: Donation
        get() = _homeMusicList

    fun uploadDonation() {
        viewModelScope.launch {
            repository.uploadDonation(_homeMusicList)
        }
    }
}
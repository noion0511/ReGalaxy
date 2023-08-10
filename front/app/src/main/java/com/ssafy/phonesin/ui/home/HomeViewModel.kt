package com.ssafy.phonesin.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.phonesin.model.Rank
import com.ssafy.phonesin.network.NetworkResponse
import com.ssafy.phonesin.repository.donation.DonationRepository
import com.ssafy.phonesin.ui.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val donationRepository: DonationRepository
) : BaseViewModel() {

    private val _ranking = MutableLiveData<List<Rank>>()
    val donationRank: MutableLiveData<List<Rank>>
        get() = _ranking

    fun getRank() {
        viewModelScope.launch {
            val response = donationRepository.getRank()
            when (response) {
                is NetworkResponse.Success -> {
                    _ranking.value = response.body.donation
                }

                else -> {}
            }
        }
    }
}
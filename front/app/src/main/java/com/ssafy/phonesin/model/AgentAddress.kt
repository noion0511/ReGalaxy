package com.ssafy.phonesin.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AgentAddress(
    val name: String,
    val address: String,
    val distance: Int,
    val latitude: Double,
    val longitude: Double
) : Parcelable
package com.ssafy.phonesin.network

import com.ssafy.phonesin.network.service.AuthApiService

class ApiServiceHolder {
    var apiService: AuthApiService? = null

    fun setAPIService(apiService: AuthApiService) {
        this.apiService = apiService
    }
}

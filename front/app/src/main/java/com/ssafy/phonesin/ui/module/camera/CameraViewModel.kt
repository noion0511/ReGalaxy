package com.ssafy.phonesin.ui.module.camera

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.phonesin.model.Event
import com.ssafy.phonesin.model.PhotoResponse
import com.ssafy.phonesin.network.NetworkResponse
import com.ssafy.phonesin.repository.ytwok.Y2KRepository
import com.ssafy.phonesin.ui.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

private const val TAG = "CameraViewModel"
@HiltViewModel
class CameraViewModel @Inject constructor(
    private val repository: Y2KRepository
) : BaseViewModel() {

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    private val _photoResponse = MutableLiveData<Event<PhotoResponse>>()
    val photoResponse: LiveData<Event<PhotoResponse>> = _photoResponse

    fun uploadImage(imageFile: File) {
        viewModelScope.launch {
            val requestFile = RequestBody.create(
                MediaType.parse("image/*"),
                imageFile
            )

            val body = MultipartBody.Part.createFormData(
                "file",
                imageFile.name,
                requestFile
            )

            val response = repository.uploadImage(body)
            Log.d(TAG, "uploadImage: $response")

            val type = "y2k 사진"
            when (response) {
                is NetworkResponse.Success -> {
                    _photoResponse.postValue(Event(response.body))
                }

                is NetworkResponse.ApiError -> {
                    _msg.postValue(postValueEvent(0, type))
                }

                is NetworkResponse.NetworkError -> {
                    _msg.postValue(postValueEvent(1, type))
                }

                is NetworkResponse.UnknownError -> {
                    _msg.postValue(postValueEvent(2, type))
                }
            }
        }
    }

    private var selectedImageId = -1

    fun selectImageId(imageId : Int) {
        selectedImageId = imageId
    }

    fun getSelectedImageId() : Int {
        return selectedImageId
    }
}
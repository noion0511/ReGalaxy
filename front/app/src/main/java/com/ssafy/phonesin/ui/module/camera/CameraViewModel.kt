package com.ssafy.phonesin.ui.module.camera

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.phonesin.ApplicationClass.Companion.prefs
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

    private val _printCount = MutableLiveData<Int>(getPrintCountFromPrefs())
    val printCount: LiveData<Int> = _printCount

    private val _photoPaths = MutableLiveData<List<String>>()
    val photoPaths: LiveData<List<String>> = _photoPaths

    private val _selectedFrameColor = MutableLiveData<Int>()
    val selectedFrameColor: LiveData<Int> = _selectedFrameColor

    private val _selectedImagePath = MutableLiveData<String>()
    val selectedImagePath: LiveData<String> = _selectedImagePath


    fun setSelectedFrameColor(color: Int) {
        _selectedFrameColor.value = color
    }

    fun setSelectedImagePath(path: String) {
        _selectedImagePath.value = path
    }

    fun updatePhotoPaths(paths: List<String>) {
        _photoPaths.value = paths
    }

    fun getPrintCountFromPrefs(): Int {
        return prefs.getInt("PRINT_COUNT", 0)
    }

    private fun savePrintCountToPrefs(count: Int) {
        with(prefs.edit()) {
            putInt("PRINT_COUNT", count)
            apply()
        }
    }

    fun increasePrintCount() {
        val newCount = (_printCount.value ?: 0) + 1
        _printCount.value = newCount
        savePrintCountToPrefs(newCount)
    }

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
}
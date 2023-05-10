package com.example.storyapp.ui.upload

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.api.response.ResponseRegisterAndStory
import com.example.storyapp.helper.AuthPreference
import com.example.storyapp.helper.reduceFileImage
import com.example.storyapp.ui.main.MainViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UploadViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        val TAG = "UploadViewModel"
    }

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun uploadStory(deskripsi: String, getFile: File) {

        val authPreference = AuthPreference(getApplication())
        val key = authPreference.getValue("key")

        val file = reduceFileImage(getFile)

        val description = deskripsi.toRequestBody("text/plain".toMediaType())
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            requestImageFile
        )

        val apiService = ApiConfig().getApiService()
        val uploadImageRequest = apiService.uploadStory("Bearer $key", imageMultipart, description)
        uploadImageRequest.enqueue(object : Callback<ResponseRegisterAndStory> {
            override fun onResponse(
                call: Call<ResponseRegisterAndStory>,
                response: Response<ResponseRegisterAndStory>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _isSuccess.value = true
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e(TAG, "$errorBody")
                }
            }
            override fun onFailure(call: Call<ResponseRegisterAndStory>, t: Throwable) {
                _isSuccess.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


}
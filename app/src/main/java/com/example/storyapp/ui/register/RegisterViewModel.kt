package com.example.storyapp.ui.register

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.api.request.RegisterRequest
import com.example.storyapp.api.response.ResponseRegisterAndStory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val context: Context) : ViewModel() {
    companion object {
        private const val TAG = "RegisterViewModel"
    }
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun register(registerRequest: RegisterRequest) {
        val apiService = ApiConfig().getApiService()
        val register = apiService.register(registerRequest)
        register.enqueue(object : Callback<ResponseRegisterAndStory> {
            override fun onResponse(
                call: Call<ResponseRegisterAndStory>,
                response: Response<ResponseRegisterAndStory>
            ) {
                if (response.isSuccessful) {
                    _isSuccess.value = true
                    val responseBody = response.body()
                    Log.i(TAG, "${responseBody?.message}")
                } else {
                    _isSuccess.value = false
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseRegisterAndStory>, t: Throwable) {
                _isSuccess.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
}
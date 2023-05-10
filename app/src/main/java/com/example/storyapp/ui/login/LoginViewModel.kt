package com.example.storyapp.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.api.request.LoginRequest
import com.example.storyapp.api.response.ResponseLogin
import com.example.storyapp.helper.AuthPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val context: Context) : ViewModel() {
    companion object {
        private const val TAG = "LoginViewModel"
    }

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun login(loginRequest: LoginRequest) {
        val apiService = ApiConfig().getApiService()
        val loginAccount = apiService.login(loginRequest)
        loginAccount.enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _isSuccess.value = true
                        val authPreference = AuthPreference(context)
                        authPreference.setValue("key", responseBody.loginResult.token)
                        authPreference.setValue("status", "1")
                    }
                } else {
                    _isSuccess.value = false
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                _isSuccess.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun logout() {
        val authPreference = AuthPreference(context)
        authPreference.clear()
    }
}
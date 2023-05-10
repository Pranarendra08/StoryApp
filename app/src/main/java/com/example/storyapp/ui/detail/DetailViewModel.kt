package com.example.storyapp.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.api.response.ResponseDetail
import com.example.storyapp.api.response.Story
import com.example.storyapp.helper.AuthPreference
import com.example.storyapp.ui.main.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): AndroidViewModel(application) {

    companion object{
        private const val TAG = "DetailViewModel"
    }

    private val _detailStory = MutableLiveData<Story>()
    val detailStory: LiveData<Story> = _detailStory

    fun getDetail(data: String) {
        val authPreference = AuthPreference(getApplication())
        val token = authPreference.getValue("key")

        val apiService = ApiConfig().getApiService()
        val detailRequest = apiService.getDetailStory("Bearer $token", data)
        detailRequest.enqueue(object : Callback<ResponseDetail> {
            override fun onResponse(
                call: Call<ResponseDetail>,
                response: Response<ResponseDetail>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _detailStory.value = responseBody.story
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e(TAG, "$errorBody")
                    }
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseDetail>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}
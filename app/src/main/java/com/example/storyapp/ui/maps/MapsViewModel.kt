package com.example.storyapp.ui.maps

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.api.response.ListStoryItem
import com.example.storyapp.api.response.ResponseListStory
import com.example.storyapp.helper.AuthPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel(application: Application) : AndroidViewModel(application) {

    private val _mapsStory = MutableLiveData<List<ListStoryItem>>()
    val mapsStory: LiveData<List<ListStoryItem>> = _mapsStory

    fun getStoryLocation() {
        val authPreference = AuthPreference(getApplication())
        val token = authPreference.getValue("key")

        val apiService = ApiConfig().getApiService()
        val getStory = apiService.getStoryLocation("Bearer $token", 1.0)
        getStory.enqueue(object : Callback<ResponseListStory> {
            override fun onResponse(
                call: Call<ResponseListStory>,
                response: Response<ResponseListStory>
            ) {
                val responseBody = response.body()
                if (responseBody != null && !responseBody.error) {
                    _mapsStory.value = responseBody.listStory
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e(TAG, "$errorBody")
                }
                Log.e(TAG, "onFailure: ${response.message()}")
            }

            override fun onFailure(call: Call<ResponseListStory>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "MapViewModel"
    }
}
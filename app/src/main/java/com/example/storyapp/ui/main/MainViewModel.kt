package com.example.storyapp.ui.main

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.api.response.ListStoryItem
import com.example.storyapp.api.response.ResponseListStory
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.di.Injection
import com.example.storyapp.helper.AuthPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(storyRepository: StoryRepository) : ViewModel() { //(application: Application) : AndroidViewModel(application)
//    private val _listStory = MutableLiveData<PagingData<ListStoryItem>>()
//    val listStory: LiveData<PagingData<ListStoryItem>> = _listStory

//    lateinit var storyRepository: StoryRepository

    val story: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getstory().cachedIn(viewModelScope)

    class MainViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(Injection.provideRepository(context)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

//    fun getStory() {
//        val authPreference = AuthPreference(getApplication())
//        val token = authPreference.getValue("key")
//
//        val apiService = ApiConfig().getApiService()
//        val storyRequest = apiService.getStory("Bearer $token", 1, 5)
//        storyRequest.enqueue(object : Callback<ResponseListStory> {
//            override fun onResponse(
//                call: Call<ResponseListStory>,
//                response: Response<ResponseListStory>
//            ) {
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null && !responseBody.error) {
//                        _listStory.value = responseBody.listStory
//                    } else {
//                        val errorBody = response.errorBody()?.string()
//                        Log.e(TAG, "$errorBody")
//                    }
//                    Log.e(TAG, "onFailure: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseListStory>, t: Throwable) {
//                Log.e(TAG, "onFailure: ${t.message}")
//            }
//        })
//    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}
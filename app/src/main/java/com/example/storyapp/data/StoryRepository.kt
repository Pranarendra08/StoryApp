package com.example.storyapp.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.storyapp.api.ApiService
import com.example.storyapp.api.response.ListStoryItem

class StoryRepository(private val apiService: ApiService, private val context: Context) { //private val storyDatabase: QuoteDatabase
    fun getstory(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, context)
            }
        ).liveData
    }
}
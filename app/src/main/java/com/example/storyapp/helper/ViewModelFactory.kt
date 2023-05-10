package com.example.storyapp.helper

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.di.Injection
import com.example.storyapp.ui.detail.DetailViewModel
import com.example.storyapp.ui.login.LoginViewModel
import com.example.storyapp.ui.main.MainViewModel
import com.example.storyapp.ui.maps.MapsViewModel
import com.example.storyapp.ui.register.RegisterViewModel
import com.example.storyapp.ui.upload.UploadViewModel

class ViewModelFactory private constructor( //
    private val mApplication: Application
//    private val context: Context
) : ViewModelProvider.Factory {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(mApplication) as T
//        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
//            return MainViewModel(mApplication) as T
//            @Suppress("UNCHECKED_CAST")
//            return MainViewModel(Injection.provideRepository(context)) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(UploadViewModel::class.java)) {
            return UploadViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
            return MapsViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
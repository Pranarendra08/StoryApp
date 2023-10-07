package com.example.storyapp.api

import com.example.storyapp.api.request.LoginRequest
import com.example.storyapp.api.request.RegisterRequest
import com.example.storyapp.api.response.ResponseDetail
import com.example.storyapp.api.response.ResponseListStory
import com.example.storyapp.api.response.ResponseLogin
import com.example.storyapp.api.response.ResponseRegisterAndStory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("register")
    fun register(
        @Body registerRequest: RegisterRequest
    ): Call<ResponseRegisterAndStory>

    @POST("login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<ResponseLogin>

    @GET("stories")
    suspend fun getStory(
        @Header("Authorization") authorization: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ResponseListStory

    @GET("stories/{id}")
    fun getDetailStory(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    ): Call<ResponseDetail>

    @Multipart
    @POST("stories")
    fun uploadStory(
        @Header("Authorization") authorization: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<ResponseRegisterAndStory>

    @GET("stories")
    fun getStoryLocation(
        @Header("Authorization") authorization: String,
        @Query("location") location: Double
    ): Call<ResponseListStory>
}
package com.example.storyapp.api.response

import com.google.gson.annotations.SerializedName

data class ResponseRegisterAndStory(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

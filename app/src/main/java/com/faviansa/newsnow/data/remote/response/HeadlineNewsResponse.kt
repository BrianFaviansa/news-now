package com.faviansa.newsnow.data.remote.response

import com.google.gson.annotations.SerializedName

data class HeadlineNewsResponse(

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("articles")
	val articles: List<ArticlesItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)



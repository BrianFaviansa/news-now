package com.faviansa.newsnow.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getHeadlineNews(
        @Query("country") country: String = "us",
    ) : HeadlineNewsResponse
}
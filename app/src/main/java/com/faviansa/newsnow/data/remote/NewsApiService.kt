package com.faviansa.newsnow.data.remote

import com.faviansa.newsnow.data.remote.response.EverythingNewsResponse
import com.faviansa.newsnow.data.remote.response.HeadlineNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getHeadlineNews(
        @Query("country") country: String = "us",
    ) : HeadlineNewsResponse

    @GET("everything")
    suspend fun getEverythingNews(
        @Query("language") language: String = "en",
        @Query("sortBy") sortBy: String = "relevancy",
        @Query("searchIn") searchIn: String = "title",
        @Query("q") query: String = "economy",
    ) : EverythingNewsResponse
}
package com.faviansa.newsnow.domain.repository

import androidx.paging.PagingData
import com.faviansa.newsnow.domain.model.News
import kotlinx.coroutines.flow.Flow

interface INewsRepository {

    fun getAllNews() : Flow<PagingData<News>>

    suspend fun getNews(newsId: Int) : News

    fun searchNews(query: String): Flow<PagingData<News>>

    fun getAllFavoriteNews() : Flow<PagingData<News>>

    suspend fun toggleFavoriteStatus(news: News)

    fun getFavoriteNewsIds() : Flow<List<Int>>
}
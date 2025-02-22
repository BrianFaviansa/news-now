package com.faviansa.newsnow.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.faviansa.newsnow.data.local.database.NewsDatabase
import com.faviansa.newsnow.data.mapper.DataMapper
import com.faviansa.newsnow.data.paging.NewsRemoteMediator
import com.faviansa.newsnow.data.remote.NewsApiService
import com.faviansa.newsnow.domain.model.News
import com.faviansa.newsnow.domain.repository.INewsRepository
import com.faviansa.newsnow.utils.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepository(
    private val apiService: NewsApiService,
    private val database: NewsDatabase
) : INewsRepository {

    private val newsDao = database.newsDao()
    private val favoriteNewsDao = database.favoriteNewsDao()

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllNews(): Flow<PagingData<News>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = NewsRemoteMediator(apiService, database),
            pagingSourceFactory = {newsDao.getAllNews()}
        ).flow.map { pagingData ->
            pagingData.map { newsEntity ->
                DataMapper.mapEntityToDomain(newsEntity)
            }
        }
    }

    override suspend fun getNews(newsId: Int): News {
       return newsDao.getNews(newsId)
    }

    override fun searchNews(query: String): Flow<PagingData<News>> {
        TODO("Not yet implemented")
    }

    override fun getAllFavoriteNews(): Flow<PagingData<News>> {
        TODO("Not yet implemented")
    }

    override suspend fun toggleFavoriteStatus(news: News) {
        TODO("Not yet implemented")
    }

    override fun getFavoriteNewsIds(): Flow<List<Int>> {
        TODO("Not yet implemented")
    }

}
package com.faviansa.newsnow.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.faviansa.newsnow.data.local.database.NewsDatabase
import com.faviansa.newsnow.data.mapper.DataMapper
import com.faviansa.newsnow.data.paging.HeadlineNewsPagingSource
import com.faviansa.newsnow.data.paging.NewsRemoteMediator
import com.faviansa.newsnow.data.paging.SearchPagingSource
import com.faviansa.newsnow.data.remote.NewsApiService
import com.faviansa.newsnow.domain.model.News
import com.faviansa.newsnow.domain.repository.INewsRepository
import com.faviansa.newsnow.utils.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: NewsApiService,
    private val database: NewsDatabase,
) : INewsRepository {

    private val newsDao = database.newsDao()
    private val favoriteNewsDao = database.favoriteNewsDao()

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllNews(): Flow<PagingData<News>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = NewsRemoteMediator(apiService, database),
            pagingSourceFactory = { newsDao.getAllNews() }
        ).flow.map { pagingData ->
            pagingData.map { newsEntity ->
                DataMapper.mapEntityToDomain(newsEntity)
            }
        }
    }

    override fun getHeadlineNews(): Flow<PagingData<News>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = { HeadlineNewsPagingSource(apiService) }
        ).flow.map { pagingData ->
            pagingData.map { news ->
                DataMapper.mapResponseToDomain(news)
            }
        }
    }

    override suspend fun getNews(newsId: Int): News {
        return DataMapper.mapEntityToDomain(newsDao.getNews(newsId))
    }

    override fun searchNews(query: String): Flow<PagingData<News>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchPagingSource(query, apiService) }
        ).flow.map { pagingData ->
            pagingData.map { newsEntity ->
                DataMapper.mapEntityToDomain(newsEntity)
            }
        }
    }

    override fun getAllFavoriteNews(): Flow<PagingData<News>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { favoriteNewsDao.getAllFavoriteNews() }
        ).flow.map { pagingData ->
            pagingData.map { newsEntity ->
                DataMapper.mapFavoriteToDomain(newsEntity)
            }
        }
    }

    override suspend fun toggleFavoriteStatus(news: News) {
        val isFavorite = favoriteNewsDao.isNewsFavorite(news.title)
        val favoriteNews = DataMapper.mapFavoriteToEntity(news)
        if (isFavorite) {
            favoriteNewsDao.deleteFavoriteNews(favoriteNews)
        } else {
            favoriteNewsDao.insertFavoriteNews(favoriteNews)
        }
    }

    override fun getFavoriteNewsTitles(): Flow<List<String>> {
        return favoriteNewsDao.getFavoriteTitles()
    }

}
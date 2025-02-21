package com.faviansa.newsnow.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.faviansa.newsnow.data.local.database.NewsDatabase
import com.faviansa.newsnow.data.local.entity.NewsEntity
import com.faviansa.newsnow.data.mapper.DataMapper
import com.faviansa.newsnow.data.remote.NewsApiService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val newsApiService: NewsApiService,
    private val newsDatabase: NewsDatabase
) : RemoteMediator<Int, NewsEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsEntity>
    ): RemoteMediator.MediatorResult {
        return try {
            // Determine the page key based on load type
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return RemoteMediator.MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull() ?: return RemoteMediator.MediatorResult.Success(endOfPaginationReached = true)
                    // Assuming you have a way to calculate the next page
                    (lastItem.id ?: 0) + 1
                }
            }

            // Fetch data from the remote API
            val response = newsApiService.getEverythingNews(page = page, pageSize = state.config.pageSize)

            // Insert data into the database
            newsDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    newsDatabase.newsDao().clearAllNews()
                }
                val newsEntities = response.articles?.map { article ->
                    DataMapper.mapResponseToEntity(article)
                } ?: emptyList()
                newsDatabase.newsDao().insertNews(newsEntities)
            }

            RemoteMediator.MediatorResult.Success(endOfPaginationReached = response.articles.isNullOrEmpty())
        } catch (e: IOException) {
            RemoteMediator.MediatorResult.Error(e)
        } catch (e: HttpException) {
            RemoteMediator.MediatorResult.Error(e)
        }
    }
}
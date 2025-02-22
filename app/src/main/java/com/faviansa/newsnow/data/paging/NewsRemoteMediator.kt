package com.faviansa.newsnow.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.faviansa.newsnow.data.local.database.NewsDatabase
import com.faviansa.newsnow.data.local.entity.NewsEntity
import com.faviansa.newsnow.data.local.entity.NewsRemoteKeys
import com.faviansa.newsnow.data.mapper.DataMapper
import com.faviansa.newsnow.data.remote.NewsApiService

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val apiService: NewsApiService,
    private val database: NewsDatabase,
) : RemoteMediator<Int, NewsEntity>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    private val newsDao = database.newsDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsEntity>,
    ): MediatorResult {
        try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: STARTING_PAGE_INDEX
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPage
                }
            }


            val responseData = apiService.getEverythingNews(
                page = currentPage,
                pageSize = state.config.pageSize
            ).articles

            val newsEntities = responseData?.map { article ->
                DataMapper.mapResponseToEntity(article)
            } ?: emptyList()
            val endOfPaginationReached = newsEntities.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    newsDao.deleteAllNews()
                    newsDao.deleteAllRemoteKeys()
                }

                val keys = newsEntities.map { newsEntity ->
                    NewsRemoteKeys(
                        id = newsEntity.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                newsDao.insertNews(newsEntities)
                newsDao.insertAllRemoteKeys(keys)
            }
            return MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, NewsEntity>,
    ): NewsRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                newsDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, NewsEntity>,
    ): NewsRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                newsDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, NewsEntity>,
    ): NewsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                newsDao.getRemoteKeys(id = unsplashImage.id)
            }
    }
}
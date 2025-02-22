package com.faviansa.newsnow.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.faviansa.newsnow.data.local.entity.NewsEntity
import com.faviansa.newsnow.data.mapper.DataMapper
import com.faviansa.newsnow.data.remote.NewsApiService

class SearchPagingSource(
    private val query: String,
    private val apiService: NewsApiService
) : PagingSource<Int, NewsEntity>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, NewsEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsEntity> {
        val currentPage = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = apiService.getEverythingNews(
                query = query,
                page = currentPage,
                pageSize = params.loadSize
            )
            val articles = response.articles?.map { article ->
                DataMapper.mapResponseToEntity(article)
            } ?: emptyList()

            val endOfPaginationReached = articles.isEmpty()

            LoadResult.Page(
                data = articles,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (endOfPaginationReached) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
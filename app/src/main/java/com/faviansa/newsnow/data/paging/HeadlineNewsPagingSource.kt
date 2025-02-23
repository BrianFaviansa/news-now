package com.faviansa.newsnow.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.faviansa.newsnow.data.remote.NewsApiService
import com.faviansa.newsnow.data.remote.response.ArticlesItem

class HeadlineNewsPagingSource(
    private val apiService: NewsApiService,
) : PagingSource<Int, ArticlesItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val response = apiService.getHeadlineNews(
                page = page,
                pageSize = params.loadSize
            )

            val articles = response.articles ?: emptyList()

            LoadResult.Page(
                data = articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (articles.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
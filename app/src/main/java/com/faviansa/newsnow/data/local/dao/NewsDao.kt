package com.faviansa.newsnow.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.faviansa.newsnow.data.local.entity.NewsEntity
import com.faviansa.newsnow.data.local.entity.NewsRemoteKeys
import com.faviansa.newsnow.domain.model.News

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getAllNews(): PagingSource<Int, NewsEntity>

    @Query("SELECT * FROM news WHERE id = :newsId")
    suspend fun getNews(newsId : Int) : News

    @Upsert
    suspend fun insertNews(newsList: List<NewsEntity>)

    @Query("DELETE FROM news")
    suspend fun deleteAllNews()

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    suspend fun getRemoteKeys(id: Int) : NewsRemoteKeys

    @Upsert
    suspend fun insertAllRemoteKeys(remoteKeys: List<NewsRemoteKeys>)

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAllRemoteKeys()
}
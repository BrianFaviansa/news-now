package com.faviansa.newsnow.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.faviansa.newsnow.data.local.entity.NewsEntity

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getNews(): PagingSource<Int, NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<NewsEntity>)

    @Query("DELETE FROM news")
    suspend fun clearAllNews()
}
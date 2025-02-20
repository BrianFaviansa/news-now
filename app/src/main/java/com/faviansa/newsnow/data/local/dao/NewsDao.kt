package com.faviansa.newsnow.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.faviansa.newsnow.data.local.entity.NewsEntity

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getAllNews(): PagingSource<Int, NewsEntity>

    @Upsert
    suspend fun insertListNews(listNews : List<NewsEntity>)

    @Delete
    suspend fun deleteAllNews()
}
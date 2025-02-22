package com.faviansa.newsnow.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.faviansa.newsnow.data.local.entity.FavoriteNewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteNewsDao {
    @Query("SELECT * FROM favorite_news")
    fun getAllFavoriteNews(): PagingSource<Int, FavoriteNewsEntity>

    @Upsert
    suspend fun insertFavoriteNews(news: FavoriteNewsEntity)

    @Delete
    suspend fun deleteFavoriteNews(news: FavoriteNewsEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_news WHERE id = :id)")
    suspend fun isNewsFavorite(id: Int): Boolean

    @Query("SELECT id FROM favorite_news")
    fun getFavoriteNewsIds(): Flow<List<Int>>
}
package com.faviansa.newsnow.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.faviansa.newsnow.data.local.dao.FavoriteNewsDao
import com.faviansa.newsnow.data.local.dao.NewsDao
import com.faviansa.newsnow.data.local.entity.FavoriteNewsEntity
import com.faviansa.newsnow.data.local.entity.NewsEntity
import com.faviansa.newsnow.data.local.entity.NewsRemoteKeys

@Database(
    entities = [NewsEntity::class, FavoriteNewsEntity::class, NewsRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase(){

    abstract fun newsDao() : NewsDao

    abstract fun favoriteNewsDao() : FavoriteNewsDao
}
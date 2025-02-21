package com.faviansa.newsnow.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.faviansa.newsnow.data.local.dao.NewsDao
import com.faviansa.newsnow.data.local.entity.NewsEntity

@Database(
    entities = [NewsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase(){
    abstract fun newsDao() : NewsDao

}
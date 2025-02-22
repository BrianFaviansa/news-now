package com.faviansa.newsnow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_news")
data class FavoriteNewsEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val source: String = "Unknown Source",
    val author: String = "Unknown Author",
    val title: String = "No Title",
    val description: String = "No Description",
    val url: String = "",
    val urlToImage: String = "",
    val publishedAt: String = "Unknown Date",
    val content: String = "No Content"
)
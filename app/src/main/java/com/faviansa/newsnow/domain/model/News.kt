package com.faviansa.newsnow.domain.model

data class News(
    val id: Int,
    val source: String,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)

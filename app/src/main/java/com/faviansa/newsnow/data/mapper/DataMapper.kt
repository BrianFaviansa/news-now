package com.faviansa.newsnow.data.mapper

import com.faviansa.newsnow.data.local.entity.NewsEntity
import com.faviansa.newsnow.data.remote.response.ArticlesItem
import com.faviansa.newsnow.domain.model.News

object DataMapper {

    fun mapResponseToEntity(article: ArticlesItem): NewsEntity {
        return NewsEntity(
            source = article.source?.name ?: "Unknown Source",
            author = article.author ?: "Unknown Author",
            title = article.title ?: "No Title",
            description = article.description ?: "No Description",
            url = article.url ?: "",
            urlToImage = article.urlToImage ?: "",
            publishedAt = article.publishedAt ?: "Unknown Date",
            content = article.content ?: "No Content"
        )
    }

    fun mapEntityToDomain(newsEntity: NewsEntity): News {
        return News(
            id = newsEntity.id,
            source = newsEntity.source,
            author = newsEntity.author,
            title = newsEntity.title,
            description = newsEntity.description,
            url = newsEntity.url,
            urlToImage = newsEntity.urlToImage,
            publishedAt = newsEntity.publishedAt,
            content = newsEntity.content
        )
    }
}
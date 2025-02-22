package com.faviansa.newsnow.data.mapper

import com.faviansa.newsnow.data.local.entity.FavoriteNewsEntity
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

    fun mapFavoriteToDomain(favNewsEntity: FavoriteNewsEntity): News {
        return News(
            id = favNewsEntity.id,
            source = favNewsEntity.source,
            author = favNewsEntity.author,
            title = favNewsEntity.title,
            description = favNewsEntity.description,
            url = favNewsEntity.url,
            urlToImage = favNewsEntity.urlToImage,
            publishedAt = favNewsEntity.publishedAt,
            content = favNewsEntity.content
        )
    }

    fun mapFavoriteToEntity(news: News) : FavoriteNewsEntity {
        return FavoriteNewsEntity(
            id = news.id,
            source = news.source,
            author = news.author,
            title = news.title,
            description = news.description,
            url = news.url,
            urlToImage = news.urlToImage,
            publishedAt = news.publishedAt,
            content = news.content
        )
    }
}
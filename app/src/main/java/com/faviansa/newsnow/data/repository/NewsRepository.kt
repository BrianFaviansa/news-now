package com.faviansa.newsnow.data.repository

import com.faviansa.newsnow.data.remote.NewsApiService
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApiService: NewsApiService
) {

}
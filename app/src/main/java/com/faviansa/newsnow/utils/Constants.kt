package com.faviansa.newsnow.utils

import com.faviansa.newsnow.BuildConfig

object Constants {
    const val NEWS_BASE_URL = "https://newsapi.org/v2/"
    const val NEWS_API_KEY = BuildConfig.NEWS_API_KEY

    const val ITEMS_PER_PAGE = 10

    val searchKeywords: List<String> = listOf(
        "Politics",
        "Health",
        "Environment",
        "Crime",
        "Technology",
        "Education",
        "Sports",
        "Entertainment",
        "Business",
        "Economy",
        "Election",
        "Corruption",
        "Natural disaster",
        "Climate change",
        "Human rights"
    )
}
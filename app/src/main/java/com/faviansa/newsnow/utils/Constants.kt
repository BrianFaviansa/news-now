package com.faviansa.newsnow.utils

import com.faviansa.newsnow.BuildConfig

object Constants {
    const val NEWS_BASE_URL = "https://newsapi.org/v2/"
    const val NEWS_API_KEY = BuildConfig.NEWS_API_KEY

    const val ITEMS_PER_PAGE = 10

    val searchKeywords: List<String> = listOf(
        "Election",
        "Corruption",
        "Natural disaster",
        "Politics",
        "Economy",
        "Health",
        "Environment",
        "Crime",
        "Technology",
        "Education",
        "Sports",
        "Entertainment",
        "Business",
        "Climate change",
        "Human rights"
    )
}
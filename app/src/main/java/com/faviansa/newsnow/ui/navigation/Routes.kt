package com.faviansa.newsnow.ui.navigation

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    data object Home : Routes()

    @Serializable
    data class NewsDetail(val newsLink: String) : Routes()
}
package com.faviansa.newsnow.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.faviansa.newsnow.domain.model.News
import com.faviansa.newsnow.ui.components.HeadlineNewsCard
import com.faviansa.newsnow.ui.components.NewsCard
import com.faviansa.newsnow.ui.navigation.NewsNowBottomNav
import com.faviansa.newsnow.utils.SnackbarEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen(
    onNavigate: (Int) -> Unit,
    snackbarHostState: SnackbarHostState,
    snackbarEvent: Flow<SnackbarEvent>,
    onNewsClick: (String) -> Unit,
    headlineNews: LazyPagingItems<News>,
    economicNews: LazyPagingItems<News>,
    favoriteNewsTitles: List<String>,
    onToggleFavorite: (News) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedItem by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = true) {
        snackbarEvent.collect { event ->
            snackbarHostState.showSnackbar(
                message = event.message,
                duration = event.duration
            )
        }
    }

    Scaffold(
        bottomBar = {
            NewsNowBottomNav(
                onNavigate = { index ->
                    selectedItem = index
                    onNavigate(index)
                },
                selectedItem = selectedItem
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = "Headline News",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )

            LazyRow {
                items(headlineNews.itemCount) { index ->
                    val headlineNewsItem = headlineNews[index]
                    if (headlineNewsItem != null) {
                        HeadlineNewsCard(
                            news = headlineNewsItem,
                            onNewsClick = onNewsClick,
                            isFavorite = favoriteNewsTitles.contains(headlineNewsItem.title),
                            onToggleFavorite = onToggleFavorite
                        )
                    } else {
                        LinearProgressIndicator(
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }

            Text(
                text = "Recommended for you",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn {
                items(economicNews.itemCount) { index ->
                    val economicNewsItem = economicNews[index]
                    if (economicNewsItem != null) {
                        NewsCard(
                            news = economicNewsItem,
                            onNewsClick = onNewsClick,
                            isFavorite = favoriteNewsTitles.contains(economicNewsItem.title),
                            onToggleFavorite = onToggleFavorite
                        )
                    } else {
                        LinearProgressIndicator(
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }
    }
}


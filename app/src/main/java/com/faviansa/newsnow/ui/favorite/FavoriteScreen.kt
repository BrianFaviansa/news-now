package com.faviansa.newsnow.ui.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.faviansa.newsnow.domain.model.News
import com.faviansa.newsnow.ui.components.FavoriteNewsCard
import com.faviansa.newsnow.ui.navigation.NewsNowBottomNav
import com.faviansa.newsnow.utils.SnackbarEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun FavoriteScreen(
    onNavigate: (Int) -> Unit,
    onNewsClick: (String) -> Unit,
    favoriteNews: LazyPagingItems<News>,
    favoriteNewsTitles: List<String>,
    onToggleFavorite: (News) -> Unit,
    snackbarHostState: SnackbarHostState,
    snackbarEvent: Flow<SnackbarEvent>,
    modifier: Modifier = Modifier,
) {
    var selectedItem by remember { mutableIntStateOf(2) }

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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = "Favorite News",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
            LazyColumn {
                items(favoriteNews.itemCount) {index ->
                    val favNewsItem = favoriteNews[index]
                    if (favNewsItem != null) {
                        FavoriteNewsCard(
                            news = favNewsItem,
                            onNewsClick = onNewsClick,
                            isFavorite = favoriteNewsTitles.contains(favNewsItem.title),
                            onToggleFavorite = onToggleFavorite
                        )
                    }
                }

            }

        }
    }
}

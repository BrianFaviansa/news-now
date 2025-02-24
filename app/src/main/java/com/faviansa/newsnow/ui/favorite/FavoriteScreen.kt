package com.faviansa.newsnow.ui.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.faviansa.newsnow.ui.components.FavoriteNewsCard
import com.faviansa.newsnow.ui.navigation.NewsNowBottomNav
import com.faviansa.newsnow.ui.theme.NewsNowTheme

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    onNavigate: (Int) -> Unit,
    onNewsClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedItem by remember { mutableIntStateOf(2) }

    val favoriteNews = viewModel.favoriteNews.collectAsLazyPagingItems()
    val favoriteNewsTitles by viewModel.favoriteTitles.collectAsStateWithLifecycle()

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
                            onToggleFavorite = { viewModel.toggleFavoriteStatus(favNewsItem) }
                        )
                    }
                }

            }

        }
    }
}

@Preview
@Composable
private fun FavoriteScreenPreview() {
    NewsNowTheme {
        FavoriteScreen(
            onNavigate = {},
            onNewsClick = {},
        )
    }
}
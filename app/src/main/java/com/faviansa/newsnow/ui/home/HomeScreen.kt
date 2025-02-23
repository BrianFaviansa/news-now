package com.faviansa.newsnow.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.faviansa.newsnow.ui.components.HeadlineNewsCard
import com.faviansa.newsnow.ui.components.NewsCard
import com.faviansa.newsnow.ui.navigation.NewsNowBottomNav
import com.faviansa.newsnow.utils.ToastEvent

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigate: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var selectedItem by remember { mutableIntStateOf(0) }

    val headlineNews = viewModel.headlineNews.collectAsLazyPagingItems()
    val economicNews = viewModel.economicNews.collectAsLazyPagingItems()

    LaunchedEffect(key1 = true) {
        viewModel.toastEvent.collect { event ->
            when (event) {
                is ToastEvent.Show -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
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
                            onNewsClick = { /* Handle click */ }
                        )
                    } else {
                        // Placeholder jika data null
                        Text(text = "Loading...", modifier = Modifier.padding(8.dp))
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
                            onNewsClick = { /* Handle click */ }
                        )
                    } else {
                        // Placeholder jika data null
                        Text(text = "Loading...", modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(
            onNavigate = {}
        )
    }
}
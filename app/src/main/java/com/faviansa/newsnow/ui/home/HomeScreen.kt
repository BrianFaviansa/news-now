package com.faviansa.newsnow.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.faviansa.newsnow.ui.components.HeadlineNewsCard
import com.faviansa.newsnow.ui.components.NewsCard
import com.faviansa.newsnow.ui.navigation.NewsNowBottomNav

@Composable
fun HomeScreen(
    onNavigate: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedItem by remember { mutableIntStateOf(0) }

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
                items() { news ->
                    HeadlineNewsCard(
                        news = news,
                        onNewsClick = { /* Handle click */ }
                    )
                }
            }

            Text(
                text = "Recommended for you",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn {
                items() { news ->
                    NewsCard(
                        news = news,
                        onNewsClick = { /* Handle click */ }
                    )
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
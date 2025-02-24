package com.faviansa.newsnow.ui.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.faviansa.newsnow.domain.model.News
import com.faviansa.newsnow.ui.components.NewsCard
import com.faviansa.newsnow.ui.navigation.NewsNowBottomNav
import com.faviansa.newsnow.utils.Constants.searchKeywords
import com.faviansa.newsnow.utils.SnackbarEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigate: (Int) -> Unit,
    onBackClick: () -> Unit,
    snackbarHostState: SnackbarHostState,
    snackbarEvent: Flow<SnackbarEvent>,
    onNewsClick: (String) -> Unit,
    searchedNews: LazyPagingItems<News>,
    favoriteNewsTitles: List<String>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onToggleFavorite: (News) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedItem by remember { mutableIntStateOf(1) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var isSuggestionChipsVisible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        snackbarEvent.collect { event ->
            snackbarHostState.showSnackbar(
                message = event.message,
                duration = event.duration
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        delay(500)
        focusRequester.requestFocus()
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
                .padding(horizontal = 4.dp)
        ) {
            SearchBar(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged { isSuggestionChipsVisible = it.isFocused },
                query = searchQuery,
                onQueryChange = { onSearchQueryChange(it) },
                onSearch = {
                    onSearch(searchQuery)
                    keyboardController?.hide()
                    focusManager.clearFocus()
                },
                placeholder = { Text(text = "Search...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (searchQuery.isNotEmpty()) onSearchQueryChange("")
                        }
                    ) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                    }
                },
                active = false,
                onActiveChange = {},
                content = {}
            )
            AnimatedVisibility(visible = isSuggestionChipsVisible) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(searchKeywords) { keyword ->
                        SuggestionChip(
                            onClick = {
                                onSearch(keyword)
                                onSearchQueryChange(keyword)
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            },
                            label = { Text(text = keyword) },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                labelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
            }
            LazyColumn {
                items(searchedNews.itemCount) { index ->
                    val searchedNewsItem = searchedNews[index]
                    if (searchedNewsItem != null) {
                        NewsCard(
                            news = searchedNewsItem,
                            onNewsClick = onNewsClick,
                            isFavorite = favoriteNewsTitles.contains(searchedNewsItem.title),
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



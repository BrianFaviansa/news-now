package com.faviansa.newsnow.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.faviansa.newsnow.data.repository.NewsRepository
import com.faviansa.newsnow.domain.model.News
import com.faviansa.newsnow.utils.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _snackbarEvent = Channel<SnackbarEvent>()
    val snackbarEvent = _snackbarEvent.receiveAsFlow()

    val favoriteNews: StateFlow<PagingData<News>> = repository.getAllFavoriteNews()
        .catch { exception ->
            _snackbarEvent.send(
                SnackbarEvent(message = "Something went wrong: ${exception.message}")
            )
        }
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = PagingData.empty()
        )

    val favoriteTitles: StateFlow<List<String>> = repository.getFavoriteNewsTitles()
        .catch { exception ->
            _snackbarEvent.send(
                SnackbarEvent(message = "Failed to load headline news: ${exception.message}")
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun toggleFavoriteStatus(news: News) {
        viewModelScope.launch {
            try {
                repository.toggleFavoriteStatus(news)
            } catch (e: Exception) {
                _snackbarEvent.send(
                    SnackbarEvent(message = "Something went wrong. ${e.message}")
                )
            }
        }
    }
}
package com.faviansa.newsnow.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.faviansa.newsnow.data.repository.NewsRepository
import com.faviansa.newsnow.domain.model.News
import com.faviansa.newsnow.utils.ToastEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _toastEvent = Channel<ToastEvent>()
    val toastEvent = _toastEvent.receiveAsFlow()

    val economicNews: StateFlow<PagingData<News>> = repository.getAllNews()
        .catch { exception ->
            _toastEvent.send(
                ToastEvent.Show(message = "Failed to load economic news: ${exception.message}")
            )
        }
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = PagingData.empty()
        )

    val headlineNews: StateFlow<PagingData<News>> = repository.getHeadlineNews()
        .catch { exception ->
            _toastEvent.send(
                ToastEvent.Show(message = "Failed to load headline news: ${exception.message}")
            )
        }
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = PagingData.empty()
        )

}
package com.faviansa.newsnow.ui.home

import androidx.lifecycle.ViewModel
import com.faviansa.newsnow.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

}
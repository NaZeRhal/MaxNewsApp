package com.maxrzhe.com.maxnewsapp.presentation.news_list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxrzhe.com.maxnewsapp.data.NewsCategory
import com.maxrzhe.com.maxnewsapp.domain.repository.NewsRepository
import com.maxrzhe.com.maxnewsapp.presentation.TAG
import com.maxrzhe.com.maxnewsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    var state by mutableStateOf(HomeScreenState())

    private var searchJob: Job? = null

    init {
        getTopHeadlines()
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500)
                    getTopHeadlines()
                }
            }
            is HomeScreenEvent.Refresh -> {
                state = state.copy(searchQuery = "", isSearchBarOpened = false)
                getTopHeadlines(shouldFetchFromRemote = true)
            }
            HomeScreenEvent.OnCloseSearchBar -> {
                state = state.copy(isSearchBarOpened = false)
            }
            HomeScreenEvent.OnOpenSearchBar -> {
                state = state.copy(isSearchBarOpened = !state.isSearchBarOpened)
            }
        }
    }

    private fun getTopHeadlines(
        shouldFetchFromRemote: Boolean = false,
        query: String = state.searchQuery.lowercase(),
        category: NewsCategory = NewsCategory.GENERAL
    ) {
        viewModelScope.launch {
            repository.getTopHeadlines(
                shouldFetchFromRemote = shouldFetchFromRemote,
                query = query,
                category = category
            ).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            state = state.copy(data = result.data, category = category)
                        }
                    }
                    is Resource.Error -> {
                        Log.e(TAG, "getTopHeadlines: error: ${result.message}")
                        state = state.copy(error = result.message, isLoading = false)
                    }
                    is Resource.Loading -> state = state.copy(isLoading = result.isLoading)
                }
            }
        }
    }
}
package com.maxrzhe.com.maxnewsapp.presentation.news_list

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxrzhe.com.maxnewsapp.data.NewsCategory
import com.maxrzhe.com.maxnewsapp.domain.repository.NewsRepository
import com.maxrzhe.com.maxnewsapp.navigation.AppNavigator
import com.maxrzhe.com.maxnewsapp.navigation.Destination
import com.maxrzhe.com.maxnewsapp.presentation.TAG
import com.maxrzhe.com.maxnewsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlinesViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val repository: NewsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    //    var state by mutableStateOf(TopHeadlinesScreenState())
    private val _state = MutableStateFlow(TopHeadlinesScreenState())
    val state: StateFlow<TopHeadlinesScreenState> = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
        val category =
            savedStateHandle.get<String>(Destination.TopHeadLinesScreen.CATEGORY_KEY)
                ?: NewsCategory.GENERAL.name.lowercase()

        _state.update {
            it.copy(category = category)
        }

        Log.w(TAG, "CATEGORY: ${state.value.category}")

        getTopHeadlines()
    }

    fun onEvent(event: ToHeadlinesScreenEvent) {
        when (event) {
            is ToHeadlinesScreenEvent.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = event.query)
                }
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500)
                    getTopHeadlines()
                }
            }
            is ToHeadlinesScreenEvent.Refresh -> {
                _state.update {
                    it.copy(searchQuery = "", isSearchBarOpened = false)
                }
                getTopHeadlines(shouldFetchFromRemote = true)
            }
            ToHeadlinesScreenEvent.OnCloseSearchBar -> {
                _state.update { it.copy(isSearchBarOpened = false) }
            }
            ToHeadlinesScreenEvent.OnOpenSearchBar -> {
                _state.update { it.copy(isSearchBarOpened = !state.value.isSearchBarOpened) }
            }
        }
    }

    fun onNavigateToDetailsScreen(articleId: Long) {
        appNavigator.tryNavigateTo(
            Destination.DetailScreen(articleId = articleId)
        )
    }

    private fun getTopHeadlines(
        shouldFetchFromRemote: Boolean = false,
        query: String = state.value.searchQuery.lowercase(),
        category: String = state.value.category
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
                            _state.update { it.copy(data = result.data, category = category) }
                        }
                    }
                    is Resource.Error -> {
                        Log.e(TAG, "getTopHeadlines: error: ${result.message}")
                        _state.update { it.copy(error = result.message, isLoading = false) }
                    }
                    is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }
                }
            }
        }
    }
}
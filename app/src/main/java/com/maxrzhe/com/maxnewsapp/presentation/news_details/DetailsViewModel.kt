package com.maxrzhe.com.maxnewsapp.presentation.news_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxrzhe.com.maxnewsapp.domain.repository.NewsRepository
import com.maxrzhe.com.maxnewsapp.navigation.Screen
import com.maxrzhe.com.maxnewsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: NewsRepository
) : ViewModel() {

    var state by mutableStateOf(DetailsScreenState())

    init {
        viewModelScope.launch {
            val articleId =
                savedStateHandle.get<Long>(Screen.DetailsScreen.ARTICLE_ID) ?: return@launch
            state = state.copy(isLoading = true)
            repository.getArticleById(articleId).collectLatest { result ->
                when (result) {
                    is Resource.Success -> state = state.copy(articleModel = result.data)
                    is Resource.Loading -> state = state.copy(isLoading = result.isLoading)
                    is Resource.Error -> state = state.copy(error = result.message)
                }
            }
        }
    }

}
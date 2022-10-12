package com.maxrzhe.com.maxnewsapp.presentation.news_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxrzhe.com.maxnewsapp.domain.repository.NewsRepository
import com.maxrzhe.com.maxnewsapp.navigation.Destination
import com.maxrzhe.com.maxnewsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: NewsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsScreenState())
    val state: StateFlow<DetailsScreenState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val articleId =
                savedStateHandle.get<String>(Destination.DetailScreen.ARTICLE_ID_KEY)?.toLong()
                    ?: return@launch
            _state.update { it.copy(isLoading = true) }
            repository.getArticleById(articleId).collectLatest { result ->
                _state.update {
                    when (result) {
                        is Resource.Success -> it.copy(articleModel = result.data)
                        is Resource.Loading -> it.copy(isLoading = result.isLoading)
                        is Resource.Error -> it.copy(error = result.message)
                    }
                }

            }
        }
    }

}
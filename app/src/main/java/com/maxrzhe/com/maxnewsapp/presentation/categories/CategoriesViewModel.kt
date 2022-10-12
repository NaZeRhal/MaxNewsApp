package com.maxrzhe.com.maxnewsapp.presentation.categories

import androidx.lifecycle.ViewModel
import com.maxrzhe.com.maxnewsapp.navigation.AppNavigator
import com.maxrzhe.com.maxnewsapp.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {

    private val _state = MutableStateFlow(CategoriesState())
    val state: StateFlow<CategoriesState> = _state.asStateFlow()

    fun onNavigateToTopHeadlines() {
        appNavigator.tryNavigateTo(
            route = Destination.TopHeadLinesScreen(
                category = state.value.category.lowercase()
            )
        )
    }

    fun onCategoryChange(category: String) {
        _state.update {
            it.copy(category = category)
        }
    }
}
package com.maxrzhe.com.maxnewsapp.presentation.news_list

import com.maxrzhe.com.maxnewsapp.data.NewsCategory
import com.maxrzhe.com.maxnewsapp.domain.model.ArticleModel

data class HomeScreenState(
    val data: List<ArticleModel> = emptyList(),
    val category: NewsCategory = NewsCategory.GENERAL,
    val isSearchBarOpened: Boolean = false,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val error: String? = null
)

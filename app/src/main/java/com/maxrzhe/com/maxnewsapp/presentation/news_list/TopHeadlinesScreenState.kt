package com.maxrzhe.com.maxnewsapp.presentation.news_list

import com.maxrzhe.com.maxnewsapp.data.NewsCategory
import com.maxrzhe.com.maxnewsapp.domain.model.ArticleModel

data class TopHeadlinesScreenState(
    val data: List<ArticleModel> = emptyList(),
    val category: String = NewsCategory.GENERAL.name.lowercase(),
    val isSearchBarOpened: Boolean = false,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val error: String? = null
)

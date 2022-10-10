package com.maxrzhe.com.maxnewsapp.presentation.news_details

import com.maxrzhe.com.maxnewsapp.domain.model.ArticleModel

data class DetailsScreenState(
    val articleModel: ArticleModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

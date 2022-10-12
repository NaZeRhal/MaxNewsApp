package com.maxrzhe.com.maxnewsapp.presentation.categories

import com.maxrzhe.com.maxnewsapp.data.NewsCategory

data class CategoriesState(
    val category: String = NewsCategory.GENERAL.name
)

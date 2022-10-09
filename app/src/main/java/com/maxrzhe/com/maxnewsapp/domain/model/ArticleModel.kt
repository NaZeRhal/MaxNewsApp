package com.maxrzhe.com.maxnewsapp.domain.model


import com.maxrzhe.com.maxnewsapp.data.NewsCategory
import kotlinx.serialization.Serializable

@Serializable
data class ArticleModel(
    val author: String?,
    val publishedAt: String?,
    val source: SourceModel?,
    val title: String?,
    val description: String?,
    val content: String?,
    val url: String?,
    val urlToImage: String?,
    val category: NewsCategory
)
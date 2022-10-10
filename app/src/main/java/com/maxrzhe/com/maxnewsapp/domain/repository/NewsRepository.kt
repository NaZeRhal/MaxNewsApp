package com.maxrzhe.com.maxnewsapp.domain.repository

import com.maxrzhe.com.maxnewsapp.data.NewsCategory
import com.maxrzhe.com.maxnewsapp.domain.model.ArticleModel
import com.maxrzhe.com.maxnewsapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getTopHeadlines(
        shouldFetchFromRemote: Boolean,
        query: String,
        category: NewsCategory
    ): Flow<Resource<List<ArticleModel>>>

    fun getArticleById(articleId: Long): Flow<Resource<ArticleModel?>>
}
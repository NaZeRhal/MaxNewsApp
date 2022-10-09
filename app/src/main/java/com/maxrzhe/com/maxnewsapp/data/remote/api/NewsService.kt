package com.maxrzhe.com.maxnewsapp.data.remote.api

import com.maxrzhe.com.maxnewsapp.data.NewsCategory
import com.maxrzhe.com.maxnewsapp.data.remote.dto.Article

interface NewsService {
    suspend fun getTopHeadlines(category: NewsCategory): List<Article?>
}
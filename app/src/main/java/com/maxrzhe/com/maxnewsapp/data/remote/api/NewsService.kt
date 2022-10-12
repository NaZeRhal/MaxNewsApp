package com.maxrzhe.com.maxnewsapp.data.remote.api

import com.maxrzhe.com.maxnewsapp.data.remote.dto.Article

interface NewsService {
    suspend fun getTopHeadlines(category: String): List<Article?>
}
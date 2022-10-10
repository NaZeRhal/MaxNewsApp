package com.maxrzhe.com.maxnewsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.maxrzhe.com.maxnewsapp.data.NewsCategory

@Dao
interface TopHeadlinesDao {

    @Insert
    suspend fun insertNews(news: List<TopHeadlineEntity>)

    @Query("DELETE FROM TopHeadlineEntity")
    suspend fun clearTopHeadlines()

    @Query("DELETE FROM TopHeadlineEntity WHERE category=:category")
    suspend fun clearTopHeadlinesByCategory(category: NewsCategory)

    @Query(
        """
            SELECT *
            FROM TopHeadlineEntity
            WHERE category=:category
            AND LOWER(title) LIKE '%' || LOWER(:query) || '%'
            OR LOWER(description) LIKE '%' || LOWER(:query) || '%'
            OR LOWER(content) LIKE '%' || LOWER(:query) || '%'
        """
    )
    suspend fun searchTopHeadlines(category: NewsCategory, query: String): List<TopHeadlineEntity>

    @Query("SELECT * FROM TopHeadlineEntity WHERE id=:articleId")
    suspend fun getArticleById(articleId: Long): TopHeadlineEntity?
}
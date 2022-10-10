package com.maxrzhe.com.maxnewsapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maxrzhe.com.maxnewsapp.data.NewsCategory

@Entity
data class TopHeadlineEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val author: String?,
    val publishedAt: String?,
    val title: String?,
    val description: String?,
    val content: String?,
    val url: String?,
    val urlToImage: String?,
    val sourceId: String?,
    val sourceName: String?,
    val category: NewsCategory
)

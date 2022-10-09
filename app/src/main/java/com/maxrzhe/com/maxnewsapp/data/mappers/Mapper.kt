package com.maxrzhe.com.maxnewsapp.data.mappers

import com.maxrzhe.com.maxnewsapp.data.NewsCategory
import com.maxrzhe.com.maxnewsapp.data.local.TopHeadlineEntity
import com.maxrzhe.com.maxnewsapp.data.remote.dto.Article
import com.maxrzhe.com.maxnewsapp.domain.model.ArticleModel
import com.maxrzhe.com.maxnewsapp.domain.model.SourceModel

fun Article.toEntity(category: NewsCategory): TopHeadlineEntity = TopHeadlineEntity(
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    title = title,
    url = url,
    urlToImage = urlToImage,
    sourceId = source?.id,
    sourceName = source?.name,
    category = category,
)

fun TopHeadlineEntity.toModel(): ArticleModel = ArticleModel(
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    title = title,
    url = url,
    urlToImage = urlToImage,
    category = category,
    source = SourceModel(
        id = sourceId,
        name = sourceName
    )
)
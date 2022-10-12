package com.maxrzhe.com.maxnewsapp.data.repository

import android.util.Log
import com.maxrzhe.com.maxnewsapp.data.local.NewsDB
import com.maxrzhe.com.maxnewsapp.data.mappers.toEntity
import com.maxrzhe.com.maxnewsapp.data.mappers.toModel
import com.maxrzhe.com.maxnewsapp.data.remote.api.NewsService
import com.maxrzhe.com.maxnewsapp.domain.model.ArticleModel
import com.maxrzhe.com.maxnewsapp.domain.repository.NewsRepository
import com.maxrzhe.com.maxnewsapp.presentation.TAG
import com.maxrzhe.com.maxnewsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val service: NewsService,
    private val db: NewsDB
) : NewsRepository {
    override fun getTopHeadlines(
        shouldFetchFromRemote: Boolean,
        query: String,
        category: String
    ): Flow<Resource<List<ArticleModel>>> =
        flow {
            Log.w(TAG, "getTopHeadlines category: $category")
            emit(Resource.Loading(true))
            val topHeadlineNews =
                db.topHeadlinesDao.searchTopHeadlines(category = category, query = query)
            Log.w(TAG, "topHeadlineNews: ${topHeadlineNews.map { it.category }}")
            emit(Resource.Success(data = topHeadlineNews.map { it.toModel() }))

            val isDbEmpty = topHeadlineNews.isEmpty() && query.isBlank()
            if (!isDbEmpty && !shouldFetchFromRemote) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteTopHeadlines = try {
                service.getTopHeadlines(category).filterNotNull()
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message ?: "Unexpected error"))
                null
            }

            remoteTopHeadlines?.let { news ->
                db.topHeadlinesDao.apply {
                    clearTopHeadlinesByCategory(category)
                    insertNews(news.map { it.toEntity(category) })
                    emit(
                        Resource.Success(
                            data = searchTopHeadlines(
                                query = "",
                                category = category
                            ).map { it.toModel() })
                    )
                    emit(Resource.Loading(false))
                }
            }
        }

    override fun getArticleById(articleId: Long): Flow<Resource<ArticleModel?>> = flow {
        emit(Resource.Loading(true))
        try {
            val article = db.topHeadlinesDao.getArticleById(articleId)?.toModel()
            emit(Resource.Loading(false))
            emit(Resource.Success(data = article))
        } catch (e: Exception) {
            emit(Resource.Loading(false))
            emit(Resource.Error(message = e.message ?: "Unexpected error"))
        }
    }
}
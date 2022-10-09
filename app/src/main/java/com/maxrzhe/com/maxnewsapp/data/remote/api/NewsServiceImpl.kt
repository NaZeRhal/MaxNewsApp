package com.maxrzhe.com.maxnewsapp.data.remote.api

import android.util.Log
import com.maxrzhe.com.maxnewsapp.data.NewsCategory
import com.maxrzhe.com.maxnewsapp.data.remote.dto.Article
import com.maxrzhe.com.maxnewsapp.data.remote.dto.TopHeadlinesResponse
import com.maxrzhe.com.maxnewsapp.presentation.TAG
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsServiceImpl @Inject constructor(
    private val client: HttpClient
) : NewsService {

    override suspend fun getTopHeadlines(category: NewsCategory): List<Article?> {
        return try {
            val response = client.get {
                url(
                    HttpRouts.createUrlString(
                        HttpRouts.TOP_HEADLINES,
                        mapOf(
                            "category" to category.name.lowercase(),
                            "country" to Locale.getDefault().country,
                            "pageSize" to 100,
                        )
                    )
                )
            }
            val topHeadlines: TopHeadlinesResponse = response.body()
            topHeadlines.articles ?: emptyList()
        } catch (e: RedirectResponseException) {
            // 3xx responses
            Log.i(TAG, "Error: ${e.response.status.description}")
            throw e
        } catch (e: ClientRequestException) {
            // 4xx responses
            Log.i(TAG, "Error: ${e.response.status.description}")
            throw e
        } catch (e: ServerResponseException) {
            // 5xx responses
            Log.i(TAG, "Error: ${e.response.status.description}")
            throw e
        } catch (e: Exception) {
            Log.i(TAG, "Error: ${e.message}")
            throw e
        }
    }
}
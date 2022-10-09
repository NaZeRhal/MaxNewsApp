package com.maxrzhe.com.maxnewsapp.data.remote.api

object HttpRouts {

    private const val BASE_URL = "https://newsapi.org/v2"

    const val TOP_HEADLINES = "$BASE_URL/top-headlines"
    const val EVERYTHING = "$BASE_URL/everything"

    fun createUrlString(path: String, params: Map<String, Any>): String {
        return StringBuilder(path).apply {
            append("?apiKey=ba14ee81c6434641832b1d98a123707e")
            params.forEach {
                append("&")
                append("${it.key}=${it.value}")
            }
        }.toString()
    }
}
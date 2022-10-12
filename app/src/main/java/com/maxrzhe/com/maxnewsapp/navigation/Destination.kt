package com.maxrzhe.com.maxnewsapp.navigation

sealed class Destination(protected val route: String, vararg params: String) {
    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    sealed class NoArgumentsDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }

    object CategoriesScreen : NoArgumentsDestination("categories")

    object TopHeadLinesScreen : Destination("top_headlines", "category") {
        const val CATEGORY_KEY = "category"

        operator fun invoke(category: String) = route.appendParams(
            CATEGORY_KEY to category
        )
    }

    object DetailScreen : Destination("details", "articleId") {
        const val ARTICLE_ID_KEY = "articleId"

        operator fun invoke(articleId: Long) = route.appendParams(
            ARTICLE_ID_KEY to articleId.toString()
        )
    }
}

internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)

    params.forEach {
        it.second?.toString()?.let { arg ->
            builder.append("/$arg")
        }
    }

    return builder.toString()
}

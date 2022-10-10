package com.maxrzhe.com.maxnewsapp.navigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object DetailsScreen : Screen("details_screen") {
        const val ARTICLE_ID = "articleId"
        val fullRoute = "$route/{$ARTICLE_ID}"
        fun withArgs(id: Long): String = "$route/$id"
    }
}

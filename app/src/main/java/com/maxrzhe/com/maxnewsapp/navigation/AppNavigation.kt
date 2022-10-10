package com.maxrzhe.com.maxnewsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.maxrzhe.com.maxnewsapp.navigation.Screen.DetailsScreen.ARTICLE_ID
import com.maxrzhe.com.maxnewsapp.presentation.news_details.DetailsScreen
import com.maxrzhe.com.maxnewsapp.presentation.news_list.HomeScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(onNavigateToDetails = { articleId ->
                navController.navigate(Screen.DetailsScreen.withArgs(id = articleId))
            })
        }

        composable(
            route = Screen.DetailsScreen.fullRoute,
            arguments = listOf(
                navArgument(name = ARTICLE_ID) {
                    type = NavType.LongType
                    nullable = false
                    defaultValue = 0L
                }
            )
        ) {
            DetailsScreen()
        }
    }
}
package com.maxrzhe.com.maxnewsapp.presentation.main

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.maxrzhe.com.maxnewsapp.navigation.Destination
import com.maxrzhe.com.maxnewsapp.navigation.NavHost
import com.maxrzhe.com.maxnewsapp.navigation.NavigationIntent
import com.maxrzhe.com.maxnewsapp.navigation.composable
import com.maxrzhe.com.maxnewsapp.presentation.categories.CategoriesScreen
import com.maxrzhe.com.maxnewsapp.presentation.news_details.DetailsScreen
import com.maxrzhe.com.maxnewsapp.presentation.news_list.TopHeadlinesScreen
import com.maxrzhe.com.maxnewsapp.ui.theme.MaxNewsAppTheme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    NavigationEffects(
        navigationChannel = mainViewModel.navigationChannel,
        navHostController = navController
    )

    MaxNewsAppTheme {
        NavHost(
            navController = navController,
            startDestination = Destination.CategoriesScreen
        ) {
            composable(destination = Destination.CategoriesScreen) {
                CategoriesScreen()
            }
            composable(destination = Destination.TopHeadLinesScreen) {
                TopHeadlinesScreen()
            }
            composable(destination = Destination.DetailScreen) {
                DetailsScreen()
            }
        }
    }
}

@Composable
fun NavigationEffects(
    navigationChannel: Channel<NavigationIntent>,
    navHostController: NavHostController
) {
    val activity = (LocalContext.current as? Activity)
    LaunchedEffect(activity, navHostController, navigationChannel) {
        navigationChannel.receiveAsFlow().collect { intent ->
            if ((activity?.isFinishing == true)) {
                return@collect
            }
            when (intent) {
                is NavigationIntent.NavigateBack -> {
                    if (intent.route != null) {
                        navHostController.popBackStack(intent.route, intent.inclusive)
                    } else {
                        navHostController.popBackStack()
                    }
                }
                is NavigationIntent.NavigateTo -> {
                    navHostController.navigate(intent.route) {
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) {
                                inclusive = intent.inclusive
                            }
                        }
                    }
                }
            }
        }
    }
}
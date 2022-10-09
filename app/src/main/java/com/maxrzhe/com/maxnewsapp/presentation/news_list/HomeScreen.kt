package com.maxrzhe.com.maxnewsapp.presentation.news_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.maxrzhe.com.maxnewsapp.presentation.news_list.components.DefaultTopBar
import com.maxrzhe.com.maxnewsapp.presentation.news_list.components.NewsCard
import com.maxrzhe.com.maxnewsapp.presentation.news_list.components.SearchAppBar

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    viewModel: NewsViewModel = hiltViewModel()
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = viewModel.state.isRefreshing)
    val state = viewModel.state

    val interactionSource = remember { MutableInteractionSource() }
    val keyBoardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Scaffold(topBar = {
        if (state.isSearchBarOpened) {
            SearchAppBar(
                query = state.searchQuery,
                onQueryChanged = { viewModel.onEvent(HomeScreenEvent.OnSearchQueryChange(it)) },
                onCloseClicked = { viewModel.onEvent(HomeScreenEvent.OnCloseSearchBar) },
            )
        } else {
            DefaultTopBar(onSearchClicked = { viewModel.onEvent(HomeScreenEvent.OnOpenSearchBar) })
        }
    }) { paddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
                .clickable(interactionSource = interactionSource, indication = null) {
                    keyBoardController?.hide()
                    focusManager.clearFocus(true)
                }
        ) {
            when {
                state.isLoading -> Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (state.error != null) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = state.error,
                                    fontSize = 16.sp,
                                    color = Color.Red,
                                )
                            }
                        }
                        SwipeRefresh(
                            state = swipeRefreshState,
                            onRefresh = { viewModel.onEvent(HomeScreenEvent.Refresh) }) {
                            LazyColumn() {
                                items(state.data) { article ->
                                    NewsCard(article = article)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
package com.maxrzhe.com.maxnewsapp.presentation.news_list.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable

@Composable
fun DefaultTopBar(onSearchClicked: () -> Unit) {
    TopAppBar(
        title = { Text(text = "Top Headlines") },
        actions = {
            IconButton(onClick = onSearchClicked) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "SearchIcon",
                    tint = MaterialTheme.colors.onBackground
                )
            }
        },
        backgroundColor = MaterialTheme.colors.background
    )
}
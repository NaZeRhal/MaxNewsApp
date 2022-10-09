package com.maxrzhe.com.maxnewsapp.presentation.news_list

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun DefaultTopBar(onSearchClicked: () -> Unit) {
    TopAppBar(
        title = { Text(text = "MaxNewsApp") },
        actions = {
            IconButton(onClick = onSearchClicked) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "SearchIcon",
                    tint = Color.White
                )
            }
        },
        backgroundColor = MaterialTheme.colors.background
    )
}
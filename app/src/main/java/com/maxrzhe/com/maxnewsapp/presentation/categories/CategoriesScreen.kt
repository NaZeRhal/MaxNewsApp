package com.maxrzhe.com.maxnewsapp.presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.maxrzhe.com.maxnewsapp.data.NewsCategory

@Composable
fun CategoriesScreen(
    categoriesViewModel: CategoriesViewModel = hiltViewModel()
) {
    val state = categoriesViewModel.state.collectAsState().value

    val categories by remember {
        val values = NewsCategory.values().map { it.name }
        mutableStateOf(values)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn() {
            items(categories) { category ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (category == state.category) Color.Red else Color.Blue)
                ) {
                    Text(
                        text = category,
                        fontSize = 24.sp,
                        modifier = Modifier.clickable {
                            categoriesViewModel.onCategoryChange(category)
                        },
                        color = Color.White
                    )
                }

            }
        }
        Button(onClick = {
            categoriesViewModel.onNavigateToTopHeadlines()
        }) {
            Text(text = "Go To Top Headlines")
        }
    }
}
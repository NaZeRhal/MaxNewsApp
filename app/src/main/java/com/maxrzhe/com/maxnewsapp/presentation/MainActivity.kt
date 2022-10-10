package com.maxrzhe.com.maxnewsapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.maxrzhe.com.maxnewsapp.navigation.AppNavigation
import com.maxrzhe.com.maxnewsapp.ui.theme.MaxNewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "NEWS_DEMO_APP"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MaxNewsAppTheme {
                AppNavigation(navController = navController)
            }
        }
    }
}
package com.maxrzhe.com.maxnewsapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.maxrzhe.com.maxnewsapp.presentation.main.MainScreen
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "NEWS_DEMO_APP"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}
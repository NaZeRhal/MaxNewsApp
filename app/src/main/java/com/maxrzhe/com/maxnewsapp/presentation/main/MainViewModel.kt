package com.maxrzhe.com.maxnewsapp.presentation.main

import androidx.lifecycle.ViewModel
import com.maxrzhe.com.maxnewsapp.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    appNavigator: AppNavigator
) : ViewModel() {

    val navigationChannel = appNavigator.navigationChannel
}
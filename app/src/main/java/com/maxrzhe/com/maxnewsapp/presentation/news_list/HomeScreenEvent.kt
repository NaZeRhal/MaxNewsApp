package com.maxrzhe.com.maxnewsapp.presentation.news_list

sealed class HomeScreenEvent {
    data class OnSearchQueryChange(val query: String) : HomeScreenEvent()
    object Refresh : HomeScreenEvent()
    object OnCloseSearchBar: HomeScreenEvent()
    object OnOpenSearchBar: HomeScreenEvent()
}
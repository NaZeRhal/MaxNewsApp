package com.maxrzhe.com.maxnewsapp.presentation.news_list

sealed class ToHeadlinesScreenEvent {
    data class OnSearchQueryChange(val query: String) : ToHeadlinesScreenEvent()
    object Refresh : ToHeadlinesScreenEvent()
    object OnCloseSearchBar : ToHeadlinesScreenEvent()
    object OnOpenSearchBar : ToHeadlinesScreenEvent()
}
package com.maxrzhe.com.maxnewsapp.di

import com.maxrzhe.com.maxnewsapp.data.remote.api.NewsService
import com.maxrzhe.com.maxnewsapp.data.remote.api.NewsServiceImpl
import com.maxrzhe.com.maxnewsapp.data.repository.NewsRepositoryImpl
import com.maxrzhe.com.maxnewsapp.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NewsServiceModule {


    @Binds
    @Singleton
    abstract fun bindNewService(newsServiceImpl: NewsServiceImpl): NewsService

    @Binds
    @Singleton
    abstract fun bindNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}
package com.maxrzhe.com.maxnewsapp.di

import com.maxrzhe.com.maxnewsapp.navigation.AppNavigator
import com.maxrzhe.com.maxnewsapp.navigation.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppNavigatorModule {

    @Binds
    @Singleton
    abstract fun bindAppNavigator(
        appNavigatorImpl: AppNavigatorImpl
    ): AppNavigator
}
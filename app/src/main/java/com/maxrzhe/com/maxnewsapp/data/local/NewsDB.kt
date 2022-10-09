package com.maxrzhe.com.maxnewsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TopHeadlineEntity::class], exportSchema = false, version = 1)
abstract class NewsDB : RoomDatabase() {
    abstract val topHeadlinesDao: TopHeadlinesDao
}
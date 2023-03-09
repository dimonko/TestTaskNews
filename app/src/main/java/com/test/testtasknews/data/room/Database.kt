package com.test.testtasknews.data.room

import androidx.room.Database

import androidx.room.RoomDatabase
import com.test.testtasknews.data.remote.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun articleDao() : RoomArticleDao

}
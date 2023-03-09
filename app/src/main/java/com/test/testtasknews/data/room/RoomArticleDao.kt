package com.test.testtasknews.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.test.testtasknews.data.remote.model.Article

@Dao
interface RoomArticleDao {

    @Query("SELECT * FROM article")
    fun getArticlesFromDatabase() : List<Article>

    @Insert
    fun saveArticleToDatabase(article: Article)

    @Delete
    fun deleteArticleFromDatabase(article: Article)

}
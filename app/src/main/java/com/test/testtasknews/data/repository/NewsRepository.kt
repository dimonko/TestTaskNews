package com.test.testtasknews.data.repository

import com.test.testtasknews.data.remote.model.Article

interface NewsRepository {

    suspend fun getArticles(page: Int) : List<Article>

    suspend fun getArticlesWithPopularitySorting(page : Int) : List<Article>

}
package com.test.testtasknews.data.repository

import com.test.testtasknews.data.api.service.NewsApiService
import com.test.testtasknews.data.remote.model.Article

class NewsRepositoryImpl constructor(private val newsApiService: NewsApiService) : NewsRepository {

    override suspend fun getArticles(page: Int): List<Article> =
        newsApiService.getAllArticles(page = page).articles

    override suspend fun getArticlesWithPopularitySorting(page: Int): List<Article> =
        newsApiService.getArticlesWithPopularitySort(page = page).articles

}

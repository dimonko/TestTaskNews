package com.test.testtasknews.data.api.service

import com.test.testtasknews.data.remote.model.NewsData
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("/v2/everything?domains=techcrunch.com,thenextweb.com")
    suspend fun getAllArticles(
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int
    ): NewsData

    @GET("/v2/everything?domains=techcrunch.com,thenextweb.com")
    suspend fun getArticlesWithPopularitySort(
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int,
        @Query("sortBy") sortBy: String = "popularity"
    ): NewsData

}
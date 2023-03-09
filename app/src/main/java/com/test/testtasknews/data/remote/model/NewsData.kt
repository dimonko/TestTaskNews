package com.test.testtasknews.data.remote.model

import com.google.gson.annotations.SerializedName

data class NewsData(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") var articles: List<Article>
)


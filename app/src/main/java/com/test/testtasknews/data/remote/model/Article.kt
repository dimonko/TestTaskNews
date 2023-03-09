package com.test.testtasknews.data.remote.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Article(
    @field:SerializedName("author") val author: String?,
    @PrimaryKey @field:SerializedName("title") val title: String,
    @field:SerializedName("description") val description: String?,
    @field:SerializedName("url") val url: String?,
    @field:SerializedName("urlToImage") val urlToImage: String?,
    @field:SerializedName("publishedAt") val publishedAt: String?,
    @field:SerializedName("content") val content: String?
)



package com.example.news

data class NewsResponse(val status: String,
                        val totalResults: Int,
                        val articles: List<News>)

data class News(
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)

package com.example.news

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

interface MyApi {


    @GET("top-headlines?country=us&apiKey=fd6ce1ea8b9849ae818c55035ad0d9cf&pageSize=50")
    suspend fun getNews(): NewsResponse

}


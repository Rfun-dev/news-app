package com.elearning.rekamiacademy.data.remote.api

import com.elearning.rekamiacademy.data.remote.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getNewsTopHeadlines(
        @Query("country") country : String,
        @Query("apiKey") apiKey : String
    ) : NewsResponse

    @GET("top-headlines")
    suspend fun getNewsForYou(
        @Query("country") country : String,
        @Query("apiKey") apiKey : String,
        @Query("category") category: String,
    ) : NewsResponse

}
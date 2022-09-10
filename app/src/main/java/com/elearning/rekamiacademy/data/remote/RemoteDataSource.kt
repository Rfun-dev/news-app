package com.elearning.rekamiacademy.data.remote

import com.elearning.rekamiacademy.data.remote.api.ApiService
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService,
    @Named("API_KEY")
    private val apiKey : String,
    @Named("COUNTRY")
    private val country : String,
    @Named("CATEGORY")
    private val category : String
    )
{
    suspend fun getNewsTopHeadLines() = apiService.getNewsTopHeadlines(country,apiKey)
    suspend fun getNewsForYou() = apiService.getNewsForYou(country,apiKey,category)
}
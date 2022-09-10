package com.elearning.rekamiacademy.data.remote.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    val sourceResponse : SourceResponse? = null,
    val author : String? = null,
    val title : String? = null,
    val content : String? = null,
    @SerializedName("urlToImage")
    val urlImage : String? = null,
    @SerializedName("publishedAt")
    val released : String? = null
)

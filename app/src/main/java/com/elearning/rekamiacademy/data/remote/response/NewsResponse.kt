package com.elearning.rekamiacademy.data.remote.response

data class NewsResponse(
    val status : String? = null,
    val totalResults : String? = null,
    val articles : List<ArticleResponse>? = null
)
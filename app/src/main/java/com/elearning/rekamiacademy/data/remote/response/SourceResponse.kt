package com.elearning.rekamiacademy.data.remote.response

import com.google.gson.annotations.SerializedName

data class SourceResponse(
    val id : String? = null,
    @SerializedName("name")
    val publisher : String? = null
)

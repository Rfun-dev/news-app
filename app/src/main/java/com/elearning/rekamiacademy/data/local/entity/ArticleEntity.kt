package com.elearning.rekamiacademy.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "article_entity")
@Parcelize
data class ArticleEntity(
    @PrimaryKey
    val id : Int?,
    val author : String,
    val title : String,
    val content : String,
    val urlImage : String,
    val released : String,
    val type : String,
) : Parcelable
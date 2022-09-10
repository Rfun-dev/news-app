package com.elearning.rekamiacademy.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_entity")
@Parcelize
data class FavoriteEntity(
    @PrimaryKey
    val id : Int?,
    val author : String,
    val title : String,
    val content : String,
    val urlImage : String,
    val released : String,
) : Parcelable

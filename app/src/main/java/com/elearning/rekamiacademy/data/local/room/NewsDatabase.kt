package com.elearning.rekamiacademy.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elearning.rekamiacademy.data.local.entity.ArticleEntity
import com.elearning.rekamiacademy.data.local.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class,ArticleEntity::class], version = 5, exportSchema = false)
abstract class NewsDatabase : RoomDatabase(){
    abstract fun newsDao() : NewsDao
}
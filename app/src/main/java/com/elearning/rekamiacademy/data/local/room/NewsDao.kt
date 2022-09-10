package com.elearning.rekamiacademy.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.elearning.rekamiacademy.data.local.entity.ArticleEntity
import com.elearning.rekamiacademy.data.local.entity.FavoriteEntity

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(article: FavoriteEntity)

    @Query("DELETE FROM favorite_entity WHERE id=:id")
    suspend fun deleteFavorite(id: Int)

    @Query("SELECT * FROM favorite_entity")
    fun getFavorite() : LiveData<List<FavoriteEntity>>

    @Query("SELECT COUNT(*) FROM favorite_entity WHERE id = :id")
    fun isFavorite(id : Int) : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticles(article: List<ArticleEntity>)

    @Query("DELETE FROM article_entity WHERE type=:type")
    suspend fun deleteAllArticle(type : String)


    @Query("SELECT * FROM article_entity")
    fun getArticles() : LiveData<List<ArticleEntity>>

    @Query("SELECT * FROM article_entity WHERE id=:id")
    fun getArticle(id : Int) : LiveData<ArticleEntity>
}
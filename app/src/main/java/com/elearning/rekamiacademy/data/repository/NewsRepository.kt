package com.elearning.rekamiacademy.data.repository

import androidx.lifecycle.LiveData
import com.elearning.rekamiacademy.data.local.entity.ArticleEntity
import com.elearning.rekamiacademy.data.local.entity.FavoriteEntity
import com.elearning.rekamiacademy.data.remote.Result

interface NewsRepository {
    fun getNewsTopHeadline() : LiveData<Result<List<ArticleEntity>>>

    fun getNewsForYou() : LiveData<Result<List<ArticleEntity>>>

    fun getFavorite() : LiveData<List<FavoriteEntity>>

    suspend fun addFavorite(articleEntity: ArticleEntity)

    suspend fun deleteFavorite(id : Int)

    fun isFavorite(id : Int) : Int

    fun getDetailArticle(id : Int) : LiveData<ArticleEntity>
}
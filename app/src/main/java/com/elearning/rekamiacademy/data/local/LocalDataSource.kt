package com.elearning.rekamiacademy.data.local

import com.elearning.rekamiacademy.data.local.entity.ArticleEntity
import com.elearning.rekamiacademy.data.local.entity.FavoriteEntity
import com.elearning.rekamiacademy.data.local.room.NewsDao

class LocalDataSource(
    private val newsDao : NewsDao,
) {
    suspend fun addFavorite(favoriteEntity: FavoriteEntity) = newsDao.addFavorite(favoriteEntity)

    fun getFavorite() = newsDao.getFavorite()

    suspend fun deleteFavorite(id: Int) = newsDao.deleteFavorite(id)

    fun isFavorite(id : Int) = newsDao.isFavorite(id)

    suspend fun addArticles(listArticles : List<ArticleEntity>) = newsDao.addArticles(listArticles)

    fun getArticles() = newsDao.getArticles()

    suspend fun deleteArticles(type : String) = newsDao.deleteAllArticle(type)

    fun getArticle(id : Int) = newsDao.getArticle(id)
}
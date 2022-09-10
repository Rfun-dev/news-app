package com.elearning.rekamiacademy.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.elearning.rekamiacademy.data.local.LocalDataSource
import com.elearning.rekamiacademy.data.local.entity.ArticleEntity
import com.elearning.rekamiacademy.data.local.entity.FavoriteEntity
import com.elearning.rekamiacademy.data.remote.RemoteDataSource
import com.elearning.rekamiacademy.data.remote.Result
import com.elearning.rekamiacademy.util.Constant.IMAGENOTAVAILABLE
import com.elearning.rekamiacademy.util.Constant.NEWSFORYOU
import com.elearning.rekamiacademy.util.Constant.NEWSHEADLINE

import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val remoteDataSource : RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : NewsRepository {
    override fun getNewsTopHeadline(): LiveData<Result<List<ArticleEntity>>> =
        liveData {
            try {
                val response = remoteDataSource.getNewsTopHeadLines().articles
                var idResult = 0
                val newsList = response?.map {
                    idResult += 1
                    ArticleEntity(
                        idResult,
                        it.author ?: "",
                        it.title ?: "",
                        it.content ?: "",
                        it.urlImage ?: IMAGENOTAVAILABLE,
                        it.released?.take(10) ?: "",
                        NEWSHEADLINE
                    )
                }
                localDataSource.deleteArticles(NEWSHEADLINE)
                newsList?.let { localDataSource.addArticles(it) }
            }catch (e : Exception){
                emit(Result.Error(e.message.toString()))
            }
            val localData : LiveData<Result<List<ArticleEntity>>> =
                localDataSource.getArticles().map { Result.Success(it) }
            emitSource(localData)
        }



    override fun getNewsForYou(): LiveData<Result<List<ArticleEntity>>> =
        liveData {
            try {
                val response = remoteDataSource.getNewsForYou().articles
                var idResult = 21
                val newsList = response?.map {
                    idResult += 1
                    ArticleEntity(
                        idResult,
                        it.author ?: "",
                        it.title ?: "",
                        it.content ?: "",
                        it.urlImage ?: "",
                        it.released?.take(10) ?: "",
                        NEWSFORYOU
                    )
                }
                localDataSource.deleteArticles(NEWSFORYOU)
                newsList?.let { localDataSource.addArticles(it) }
            }catch (e : Exception){
                emit(Result.Error(e.message.toString()))
            }
            val localData : LiveData<Result<List<ArticleEntity>>> =
                localDataSource.getArticles().map { Result.Success(it) }
            emitSource(localData)
        }


    override fun getFavorite(): LiveData<List<FavoriteEntity>> =
        localDataSource.getFavorite()

    override suspend fun addFavorite(articleEntity: ArticleEntity) {
        val favorite = articleEntity.let {
            FavoriteEntity(
                it.id,
                it.author,
                it.title,
                it.content,
                it.urlImage,
                it.released
            )
        }
        localDataSource.addFavorite(favorite)
    }

    override suspend fun deleteFavorite(id: Int) =
        localDataSource.deleteFavorite(id)

    override fun isFavorite(id: Int): Int =
        localDataSource.isFavorite(id)


    override fun getDetailArticle(id: Int): LiveData<ArticleEntity> =
        localDataSource.getArticle(id)
}
package com.elearning.rekamiacademy.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elearning.rekamiacademy.data.local.entity.ArticleEntity
import com.elearning.rekamiacademy.data.local.entity.FavoriteEntity
import com.elearning.rekamiacademy.data.repository.NewsRepository
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    fun getDetailNews(id : Int) = repository.getDetailArticle(id)
    suspend fun addFavorite(article : ArticleEntity) = viewModelScope.launch {
        repository.addFavorite(article)
    }

    fun isFavorite(id : Int) = repository.isFavorite(id)

    suspend fun deleteFavorite(id : Int) = viewModelScope.launch {
        repository.deleteFavorite(id)
    }
}
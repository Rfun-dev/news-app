package com.elearning.rekamiacademy.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.elearning.rekamiacademy.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel(){
    fun getFavorite() = repository.getFavorite()
}
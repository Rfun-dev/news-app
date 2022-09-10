package com.elearning.rekamiacademy.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.elearning.rekamiacademy.data.local.entity.ArticleEntity
import com.elearning.rekamiacademy.data.remote.Result
import com.elearning.rekamiacademy.data.repository.NewsRepository
import com.elearning.rekamiacademy.data.repository.NewsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    newsRepository: NewsRepository
) : ViewModel(){
    val dataNewsForYou : LiveData<Result<List<ArticleEntity>>> = newsRepository.getNewsForYou()
    val dataNewsHeadLine : LiveData<Result<List<ArticleEntity>>> = newsRepository.getNewsTopHeadline()
}

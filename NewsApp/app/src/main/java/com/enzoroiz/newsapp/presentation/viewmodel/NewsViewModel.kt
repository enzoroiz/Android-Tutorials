package com.enzoroiz.newsapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.enzoroiz.newsapp.data.model.Article
import com.enzoroiz.newsapp.data.model.NewsResponse
import com.enzoroiz.newsapp.domain.repository.Resource
import com.enzoroiz.newsapp.domain.usecase.GetNewsHeadlinesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase): ViewModel(){
    private val getNewsHeadlinesMutableLiveData = MutableLiveData<Resource<NewsResponse>>()
    val getNewsHeadlinesLiveData: LiveData<Resource<NewsResponse>>
        get() = getNewsHeadlinesMutableLiveData

    fun getNewsHeadlines(page: Int, country: String) {
        getNewsHeadlinesMutableLiveData.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO).launch {
            getNewsHeadlinesMutableLiveData.postValue(getNewsHeadlinesUseCase.execute(page, country))
        }
    }
}
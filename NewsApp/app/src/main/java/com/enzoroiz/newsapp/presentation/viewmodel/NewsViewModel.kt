package com.enzoroiz.newsapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.enzoroiz.newsapp.data.model.NewsResponse
import com.enzoroiz.newsapp.domain.repository.Resource
import com.enzoroiz.newsapp.domain.usecase.GetNewsHeadlinesUseCase
import com.enzoroiz.newsapp.domain.usecase.GetSearchedNewsUseCase
import com.enzoroiz.newsapp.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class NewsViewModel(
    application: Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase
): AndroidViewModel(application) {
    private val getNewsHeadlinesMutableLiveData = MutableLiveData<Resource<NewsResponse>>()
    val getNewsHeadlinesLiveData: LiveData<Resource<NewsResponse>>
        get() = getNewsHeadlinesMutableLiveData

    private val getSearchedNewsMutableLiveData = MutableLiveData<Resource<NewsResponse>>()
    val getSearchedNewsLiveData: LiveData<Resource<NewsResponse>>
        get() = getSearchedNewsMutableLiveData

    fun getNewsHeadlines(page: Int, country: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getNewsHeadlinesMutableLiveData.postValue(Resource.Loading())

                if (Util.isNetworkAvailable(getApplication())) {
                    getNewsHeadlinesMutableLiveData.postValue(getNewsHeadlinesUseCase.execute(page, country))
                } else {
                    getNewsHeadlinesMutableLiveData.postValue(Resource.Error("Internet not available"))
                }
            } catch (e: Exception) {
                getNewsHeadlinesMutableLiveData.postValue(Resource.Error(e.message.toString()))
            }
        }
    }

    fun getSearchedNews(page: Int, country: String, search: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getSearchedNewsMutableLiveData.postValue(Resource.Loading())

                if (Util.isNetworkAvailable(getApplication())) {
                    getSearchedNewsMutableLiveData.postValue(getSearchedNewsUseCase.execute(page, country, search))
                } else {
                    getSearchedNewsMutableLiveData.postValue(Resource.Error("Internet not available"))
                }
            } catch (e: Exception) {
                getSearchedNewsMutableLiveData.postValue(Resource.Error(e.message.toString()))
            }
        }
    }
}
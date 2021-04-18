package com.enzoroiz.newsapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.enzoroiz.newsapp.data.model.NewsResponse
import com.enzoroiz.newsapp.domain.repository.Resource
import com.enzoroiz.newsapp.domain.usecase.GetNewsHeadlinesUseCase
import com.enzoroiz.newsapp.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class NewsViewModel(application: Application, private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase): AndroidViewModel(application) {
    private val getNewsHeadlinesMutableLiveData = MutableLiveData<Resource<NewsResponse>>()
    val getNewsHeadlinesLiveData: LiveData<Resource<NewsResponse>>
        get() = getNewsHeadlinesMutableLiveData

    fun getNewsHeadlines(page: Int, country: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (Util.isNetworkAvailable(getApplication())) {
                    getNewsHeadlinesMutableLiveData.postValue(Resource.Loading())
                    getNewsHeadlinesMutableLiveData.postValue(getNewsHeadlinesUseCase.execute(page, country))
                } else {
                    getNewsHeadlinesMutableLiveData.postValue(Resource.Error("Internet not available"))
                }
            } catch (e: Exception) {
                getNewsHeadlinesMutableLiveData.postValue(Resource.Error(e.message.toString()))
            }
        }
    }
}
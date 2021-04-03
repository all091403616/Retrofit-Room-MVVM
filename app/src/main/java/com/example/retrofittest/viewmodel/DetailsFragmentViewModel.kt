package com.example.retrofittest.viewmodel

import androidx.lifecycle.*
import com.example.retrofittest.model.datamodel.News
import com.example.retrofittest.repository.NewsRepository
import kotlinx.coroutines.launch

class DetailsFragmentViewModel(private val repository: NewsRepository):ViewModel() {

    private val _news=MutableLiveData<News>()
    val news:LiveData<News> =_news

    fun getData(id:Long){
        viewModelScope.launch {
            val news: News =repository.getOne(id)
            _news.value=news
        }
    }

    fun getId():String{
        return _news.value?.rowId?.toString()!!
    }

    fun delete(){
        viewModelScope.launch {
            repository.delete(_news.value!!)
        }
    }
}

class DetailsViewModelFactory(private val repository: NewsRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsFragmentViewModel::class.java))
            return DetailsFragmentViewModel(repository) as T
        throw Exception("")
    }
}
package com.example.retrofittest.viewmodel

import androidx.lifecycle.*
import com.example.retrofittest.model.datamodel.News
import com.example.retrofittest.repository.NewsRepository
import com.example.retrofittest.utils.CheckConnection
import com.example.retrofittest.utils.States
import kotlinx.coroutines.launch

class ListViewModel(private val connection: CheckConnection, private val repository:NewsRepository):ViewModel() {


    private val searchText=MutableLiveData<String>()
    val news:LiveData<List<News>> = Transformations.switchMap(searchText){
        repository.getNews(it).asLiveData()
    }

    private val _state=MutableLiveData<States>()
    val state:LiveData<States> = _state

    init {
        searchText.value="%%"
    }

    fun fetchNewData(){
        if (connection.isConnect())
            viewModelScope.launch {
                try {
                    _state.value=States.LOADING
                    repository.getDataFromServer()
                    _state.value=States.SUCCESS
                }catch (ex:Exception){
                    _state.value=States.ERROR
                }
            }
        else
            _state.value=States.NETWORK
    }

    fun searchNews(text:String){
        searchText.value="%$text%"
    }

    fun stateReceived(){
        _state.value=States.SUCCESS
    }
}

class ListViewModelFactory(private val checkConnection: CheckConnection,private val repository: NewsRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java))
            return ListViewModel(checkConnection,repository) as T
        throw Exception("")
    }
}
package com.example.retrofittest.viewmodel

import androidx.lifecycle.*
import com.example.retrofittest.model.datamodel.News
import com.example.retrofittest.repository.NewsRepository
import com.example.retrofittest.utils.CheckConnection
import com.example.retrofittest.utils.States
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ListViewModel(private val connection: CheckConnection, private val repository:NewsRepository):ViewModel() {


    private val searchText=MutableLiveData<String>()
    val news:LiveData<List<News>> = Transformations.switchMap(searchText){
        repository.getNews(it).asLiveData()
    }

    private val _message=MutableLiveData<String?>()
    val message:LiveData<String?> =_message

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
                    _message.value="Please try again later"
                    _state.value=States.ERROR
                }
            }
        else
            _message.value="Check your internet connection"
    }

    fun searchNews(text:String){
        searchText.value="%$text%"
    }

    fun messageReceived(){
        _message.value=null
    }

    fun getSearchHint()="Search..."
}

class ListViewModelFactory(private val checkConnection: CheckConnection,private val repository: NewsRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java))
            return ListViewModel(checkConnection,repository) as T
        throw Exception("")
    }
}
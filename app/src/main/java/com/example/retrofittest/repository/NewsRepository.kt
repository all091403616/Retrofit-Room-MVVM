package com.example.retrofittest.repository

import com.example.retrofittest.model.datamodel.News
import com.example.retrofittest.model.local.NewsDAO
import com.example.retrofittest.model.remote.RetrofitService
import kotlinx.coroutines.flow.Flow

class NewsRepository private constructor(private val newsDAO: NewsDAO){

    companion object{
        private var INSTANCE:NewsRepository?=null
        fun getInstance(newsDAO: NewsDAO):NewsRepository{
            if (INSTANCE==null)
                INSTANCE= NewsRepository(newsDAO)
            return INSTANCE!!
        }
    }

    fun getNews(search:String):Flow<List<News>>{
        return newsDAO.selectNotes(search)
    }

    suspend fun getOne(id:Long)=newsDAO.getOne(id)

    suspend fun getDataFromServer(){
        try {
            val response = RetrofitService.apiService.getAll()
            newsDAO.insertNews(response.news)
        }catch (ex:Exception){
            throw ex
        }
    }

    suspend fun delete(news: News){
        newsDAO.deleteOne(news)
    }
}
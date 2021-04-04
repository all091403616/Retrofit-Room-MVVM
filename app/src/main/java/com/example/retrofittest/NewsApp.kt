package com.example.retrofittest

import android.app.Application
import com.example.retrofittest.model.local.NewsDAO
import com.example.retrofittest.model.local.NewsDatabase
import com.example.retrofittest.repository.NewsRepository
import com.example.retrofittest.utils.CheckConnection

class NewsApp:Application() {
    val link:String by lazy { "https://retrofit.mvvm/?id=" }
    val checkConnection:CheckConnection by lazy { CheckConnection(this) }
    private val newsDAO:NewsDAO by lazy { NewsDatabase.getInstance(this).newsDao() }
    val repository:NewsRepository by lazy { NewsRepository.getInstance(newsDAO) }
}
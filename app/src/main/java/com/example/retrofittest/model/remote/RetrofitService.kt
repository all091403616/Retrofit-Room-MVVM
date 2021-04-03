package com.example.retrofittest.model.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private val BASE_URL="https://api.currentsapi.services/v1/"

    private val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService:ApiService= retrofit.create(ApiService::class.java)
}
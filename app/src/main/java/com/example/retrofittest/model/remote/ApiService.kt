package com.example.retrofittest.model.remote

import com.example.retrofittest.model.remote.datamodel.ReceiveDataModel
import retrofit2.http.GET

interface ApiService {
    @GET("latest-news?apiKey=UQKjErEOmEIobg21e6ZLNCImgXihr2fQBuAS4Pn9pudLbEn-")
    suspend fun getAll():ReceiveDataModel
}
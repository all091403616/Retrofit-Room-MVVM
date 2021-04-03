package com.example.retrofittest.model.remote.datamodel

import com.example.retrofittest.model.datamodel.News
import java.io.Serializable

data class ReceiveDataModel (
    val news:ArrayList<News>
    ):Serializable
package com.example.retrofittest.model.datamodel

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["id"],unique = true)])
data class News(
        @PrimaryKey(autoGenerate = true)
        var rowId: Long,
        var id: String,
        var author: String?,
        var title: String,
        var description: String,
        var image: String?,
        var published: String
)
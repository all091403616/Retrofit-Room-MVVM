package com.example.retrofittest.model.local

import androidx.room.*
import com.example.retrofittest.model.datamodel.News
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNews(news: List<News>)

    @Query("SELECT * FROM News WHERE description LIKE :search ORDER BY rowId DESC")
    fun selectNotes(search:String):Flow<List<News>>

    @Query("SELECT * FROM News WHERE rowId=:id")
    suspend fun getOne(id:Long):News

    @Delete
    suspend fun deleteOne(news:News)
}
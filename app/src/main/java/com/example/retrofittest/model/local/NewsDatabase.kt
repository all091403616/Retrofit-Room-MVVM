package com.example.retrofittest.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.retrofittest.model.datamodel.News


@Database(entities = [News::class],version = 1)
abstract class NewsDatabase:RoomDatabase() {
    abstract fun newsDao():NewsDAO

    companion object{
        @Volatile
        private var INSTANCE:NewsDatabase?=null
        fun getInstance(context: Context):NewsDatabase{
            return INSTANCE?: synchronized(this){
                val instance=Room.databaseBuilder(
                        context,NewsDatabase::class.java,"newsDb"
                ).build()
                INSTANCE=instance
                INSTANCE!!
            }
        }
    }
}
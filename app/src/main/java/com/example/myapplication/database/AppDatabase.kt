package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.models.Posts

@Database(entities = [Posts::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDao() : AppDao
}
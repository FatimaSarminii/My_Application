package com.example.myapplication.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.myapplication.app.network.AppModule
import com.example.myapplication.app.network.NetworkHelper
import com.example.myapplication.data.repositories.AppRepository
import com.example.myapplication.database.AppDao
import com.example.myapplication.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val DataModule = module {

    single { AppModule() }

    fun provideDataBase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDao(dataBase: AppDatabase): AppDao {
        return dataBase.appDao()
    }
    single { provideDataBase(androidApplication()) }
    single { provideDao(get()) }


}


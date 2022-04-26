package com.example.myapplication.helper

import android.app.Application
import com.example.myapplication.di.DataModule
import com.example.myapplication.di.repositoryModule
import com.example.myapplication.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    DataModule,
                )
            )
        }
    }
}
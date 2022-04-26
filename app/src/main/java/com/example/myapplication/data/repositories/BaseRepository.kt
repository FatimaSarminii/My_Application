package com.example.myapplication.data.repositories

import com.example.myapplication.app.network.AppModule
import com.example.myapplication.database.AppDao
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


open class BaseRepository : KoinComponent {

    protected val BASE_URL: String = "https://jsonplaceholder.typicode.com/"
    protected val appModule: AppModule by inject()
    protected val dao: AppDao by inject()


}
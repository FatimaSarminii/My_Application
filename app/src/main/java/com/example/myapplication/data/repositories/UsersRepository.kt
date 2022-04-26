package com.example.myapplication.data.repositories

import com.example.myapplication.data.models.Users

class UsersRepository : BaseRepository() {

    suspend fun getUsers(): List<Users> {
        return appModule.provideRetrofitInstance(BASE_URL).getUsersList()
    }

}
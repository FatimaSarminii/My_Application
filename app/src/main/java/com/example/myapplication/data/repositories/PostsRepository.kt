package com.example.myapplication.data.repositories

import com.example.myapplication.data.models.Posts
import com.example.myapplication.data.models.Users

class PostsRepository : BaseRepository() {

    suspend fun getPosts(): List<Posts> {
        return appModule.provideRetrofitInstance(BASE_URL).getPostsList()
    }
}
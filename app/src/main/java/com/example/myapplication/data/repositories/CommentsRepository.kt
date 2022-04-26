package com.example.myapplication.data.repositories

import com.example.myapplication.data.models.Comments

class CommentsRepository : BaseRepository() {

    suspend fun getComments(id : String): List<Comments> {
        return appModule.provideRetrofitInstance(BASE_URL).getCommentsById(id)
    }
}
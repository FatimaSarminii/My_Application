package com.example.myapplication.data.repositories

import androidx.lifecycle.LiveData
import com.example.myapplication.data.models.Posts
import com.example.myapplication.database.AppDao

class AppRepository(): BaseRepository() {

      val readAllPosts: LiveData<List<Posts>> = dao.getAllPosts()

      fun addPostToFav(post: Posts){
        dao.insertPost(post)
    }

    fun isFavorite(id: String): Boolean{
        var favList = dao.getFavPost(id)
        return favList.isNotEmpty()
    }

    fun deletePostFromFav(id: String){
        dao.deletePost(id)
    }



}

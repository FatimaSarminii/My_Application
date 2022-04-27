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

    fun deleteAllFavPosts(){
        dao.deleteAll()
    }

    fun search(searchQuery: String): LiveData<List<Posts>> {
       return dao.search(searchQuery)
    }


    fun sortByASC(): LiveData<List<Posts>> {
        return dao.sortByASC()
    }

    fun sortByDESC(): LiveData<List<Posts>> {
        return dao.sortByDESC()
    }


}

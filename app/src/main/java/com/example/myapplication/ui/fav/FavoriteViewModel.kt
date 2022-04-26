package com.example.myapplication.ui.fav

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.models.Posts
import com.example.myapplication.data.repositories.AppRepository
import com.example.myapplication.database.AppDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel( private val appRepository : AppRepository) : ViewModel() {

    var responsePosts: LiveData<List<Posts>>? = null

    init {
        getAllPosts()
    }


    private fun getAllPosts() = viewModelScope.launch {
        val posts = appRepository.readAllPosts

        if (posts != null) {
            responsePosts = posts

        }
    }

    fun removeFromFavorite(id: Int) {
        viewModelScope.launch(Dispatchers.IO){
            appRepository.deletePostFromFav(id.toString())
        }
    }



}





package com.example.myapplication.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.models.Comments
import com.example.myapplication.data.models.Posts
import com.example.myapplication.data.repositories.AppRepository
import com.example.myapplication.data.repositories.CommentsRepository
import com.example.myapplication.database.AppDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(private val appRepository: AppRepository, private val commentsRepository : CommentsRepository) : ViewModel() {

    private val commentsLiveData = MutableLiveData<List<Comments>>()
    val responseComments: LiveData<List<Comments>> = commentsLiveData

    var isFavorite: Boolean = false

    fun getCommentsById(id: String) = viewModelScope.launch {
        val comments = commentsRepository.getComments(id)

        if (comments != null) {
            commentsLiveData.postValue(comments)
        }
    }

    fun addToFavorite(post: Posts) {
        viewModelScope.launch(Dispatchers.IO){
            appRepository.addPostToFav(post)
        }
    }

    fun CheckFavorite(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            isFavorite = appRepository.isFavorite(id.toString())
        }
    }

    fun removeFromFavorite(id: Int) {
        viewModelScope.launch(Dispatchers.IO){
            appRepository.deletePostFromFav(id.toString())
        }
    }


}
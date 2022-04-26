package com.example.myapplication.ui.posts

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.myapplication.app.network.NetworkHelper
import com.example.myapplication.data.models.Posts
import com.example.myapplication.data.models.Resource
import com.example.myapplication.data.models.Users
import com.example.myapplication.data.repositories.AppRepository
import com.example.myapplication.data.repositories.PostsRepository
import com.example.myapplication.data.repositories.UsersRepository
import com.example.myapplication.database.AppDao
import com.example.myapplication.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Appendable
import java.lang.Exception

class PostsViewModel( private val posts_repository : PostsRepository,
        private val users_repository : UsersRepository) : ViewModel() {

     private val postsLiveData = MutableLiveData<Resource<List<Posts>>>()
     var responsePosts: LiveData<Resource<List<Posts>>> = postsLiveData


    init{
        getAllPosts()
    }


     private fun getAllPosts()  = viewModelScope.launch {

         postsLiveData.postValue(Resource.loading(null))

         val posts = posts_repository.getPosts()
         val users = users_repository.getUsers()

             posts.let {

                 if (posts != null) {
                     posts.map { posts ->
                         var postUser = users.find { it.id == posts.userId }
                         if (postUser != null) {
                             posts.userName = postUser.username
                         }
                     }
                     postsLiveData.postValue(Resource.success(posts))

                 } else postsLiveData.postValue(Resource.error("No posts yet", null))
             }

     }



}





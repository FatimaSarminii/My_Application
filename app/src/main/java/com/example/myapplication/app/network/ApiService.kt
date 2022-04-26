package com.example.myapplication.app.network

import com.example.myapplication.data.models.Comments
import com.example.myapplication.data.models.Posts
import com.example.myapplication.data.models.Users
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("posts")
    suspend fun getPostsList(): List<Posts>

    @GET("users")
    suspend fun getUsersList(): List<Users>

    @GET("/post/{id}/comments")
    suspend fun getCommentsById(@Path("id") id: String): List<Comments>
}

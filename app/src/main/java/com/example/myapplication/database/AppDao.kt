package com.example.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.data.models.Posts

@Dao
interface AppDao {

    @Query("SELECT * FROM posts_table")
     fun getAllPosts(): LiveData<List<Posts>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insertPost(post: Posts)

    @Query("SELECT * FROM posts_table WHERE id = :id")
    fun getFavPost(id : String): List<Posts>

    @Query("DELETE FROM posts_table WHERE id = :id")
    fun deletePost(id: String)


}
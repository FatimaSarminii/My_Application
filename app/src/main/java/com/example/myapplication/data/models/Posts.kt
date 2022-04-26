package com.example.myapplication.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts_table")
data class Posts(
    @PrimaryKey
    var id: Int,
    var body: String,
    var title: String,
    var userId: Int,
    var userName: String = "",
    var isFavorite: Boolean = false
)
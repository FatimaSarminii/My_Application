package com.example.myapplication.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class Users(
    @PrimaryKey
    var id: Int,
    var username: String,
    )
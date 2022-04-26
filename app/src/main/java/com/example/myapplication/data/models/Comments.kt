package com.example.myapplication.data.models

data class Comments(
    var id: Int,
    var postId: Int,
    var email: String?,
    var name: String?,
    var body: String?,
    )

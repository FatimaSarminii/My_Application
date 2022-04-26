package com.example.myapplication.di

import com.example.myapplication.data.repositories.AppRepository
import com.example.myapplication.data.repositories.PostsRepository
import com.example.myapplication.database.AppDao
import com.example.myapplication.ui.details.DetailsViewModel
import com.example.myapplication.ui.fav.FavoriteViewModel
import com.example.myapplication.ui.posts.PostsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PostsViewModel(get(),get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailsViewModel(get(),get()) }

}
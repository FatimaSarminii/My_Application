package com.example.myapplication.di

import com.example.myapplication.data.repositories.*
import org.koin.dsl.module

val repositoryModule = module {

    single { BaseRepository() }
    single { AppRepository() }
    single { PostsRepository() }
    single { UsersRepository() }
    single { CommentsRepository() }

}
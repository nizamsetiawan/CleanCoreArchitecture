package com.example.app.dicodingeventsapp.di

import com.example.app.dicodingeventsapp.data.local.LocalDataSource
import com.example.app.dicodingeventsapp.data.remote.RemoteDataSource
import com.example.app.dicodingeventsapp.data.repository.LocalRepositoryImpl
import com.example.app.dicodingeventsapp.data.repository.RemoteRepositoryImpl
import com.example.app.dicodingeventsapp.domain.repository.LocalRepository
import com.example.app.dicodingeventsapp.domain.repository.RemoteRepository
import org.koin.dsl.module

val repositoryModules = module {
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get()) }
    single<RemoteRepository> { RemoteRepositoryImpl(get()) }
    single<LocalRepository> { LocalRepositoryImpl(get()) }
}


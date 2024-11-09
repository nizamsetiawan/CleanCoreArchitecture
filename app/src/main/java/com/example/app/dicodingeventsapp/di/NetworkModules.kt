package com.example.app.dicodingeventsapp.di

import com.example.app.dicodingeventsapp.data.remote.network.ApiConfig
import org.koin.dsl.module

val networkModules = module {
    single { ApiConfig.provideGson() }
    single { ApiConfig.provideOkHttpClient() }
    single { ApiConfig.provideRetrofit(get(), get()) }
    single { ApiConfig.provideApiService(get()) }
}

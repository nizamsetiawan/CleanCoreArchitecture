package com.example.app.dicodingeventsapp.di

import EventViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { EventViewModel(get(), get(), get(), get(), get()) }
}



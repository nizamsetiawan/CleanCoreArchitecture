package com.example.app.dicodingeventsapp.di

import com.example.app.dicodingeventsapp.domain.useCase.GetEventDetailUseCase
import com.example.app.dicodingeventsapp.domain.useCase.GetFinishedEventsUseCase
import com.example.app.dicodingeventsapp.domain.useCase.GetUpcomingEventsUseCase
import com.example.app.dicodingeventsapp.domain.useCase.SearchEventsUseCase
import org.koin.dsl.module

val useCaseModules = module {
    factory { GetUpcomingEventsUseCase(get()) }
    factory { GetFinishedEventsUseCase(get()) }
    factory { GetEventDetailUseCase(get()) }
    factory { SearchEventsUseCase(get()) }
}
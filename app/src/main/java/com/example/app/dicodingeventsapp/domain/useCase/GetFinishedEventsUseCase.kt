package com.example.app.dicodingeventsapp.domain.useCase

import com.example.app.dicodingeventsapp.data.remote.response.ListEventsItem
import com.example.app.dicodingeventsapp.domain.repository.RemoteRepository
import com.example.app.dicodingeventsapp.presentation.utils.ResponseState

class GetFinishedEventsUseCase(private val repository: RemoteRepository) {
    suspend operator fun invoke(): ResponseState<List<ListEventsItem>> {
        return repository.getFinishedEvents()
    }
}

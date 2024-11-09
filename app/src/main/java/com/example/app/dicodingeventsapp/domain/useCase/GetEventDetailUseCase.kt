package com.example.app.dicodingeventsapp.domain.useCase

import com.example.app.dicodingeventsapp.data.remote.response.Event
import com.example.app.dicodingeventsapp.domain.repository.RemoteRepository
import com.example.app.dicodingeventsapp.presentation.utils.ResponseState

class GetEventDetailUseCase(private val repository: RemoteRepository) {
    suspend operator fun invoke(id: String): ResponseState<Event> {
        return repository.getEventDetail(id)
    }
}

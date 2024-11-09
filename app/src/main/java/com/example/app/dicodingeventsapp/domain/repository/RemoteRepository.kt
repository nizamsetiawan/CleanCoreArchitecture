package com.example.app.dicodingeventsapp.domain.repository

import com.example.app.dicodingeventsapp.data.remote.response.Event
import com.example.app.dicodingeventsapp.data.remote.response.ListEventsItem
import com.example.app.dicodingeventsapp.presentation.utils.ResponseState

interface RemoteRepository {
    suspend fun getUpcomingEvents(): ResponseState<List<ListEventsItem>>
    suspend fun getFinishedEvents(): ResponseState<List<ListEventsItem>>
    suspend fun searchEvents(keyword: String): ResponseState<List<ListEventsItem>>
    suspend fun getEventDetail(id: String): ResponseState<Event>
}

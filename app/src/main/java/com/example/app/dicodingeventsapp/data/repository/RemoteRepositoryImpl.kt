package com.example.app.dicodingeventsapp.data.repository

import com.example.app.dicodingeventsapp.data.remote.RemoteDataSource
import com.example.app.dicodingeventsapp.data.remote.response.Event
import com.example.app.dicodingeventsapp.data.remote.response.ListEventsItem
import com.example.app.dicodingeventsapp.domain.repository.RemoteRepository
import com.example.app.dicodingeventsapp.presentation.utils.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteRepositoryImpl(private val remoteDataSource: RemoteDataSource) : RemoteRepository {

    override suspend fun getUpcomingEvents(): ResponseState<List<ListEventsItem>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getUpcomingEvents()
                ResponseState.Success(response.listEvents)
            } catch (e: Exception) {
                ResponseState.Error(e.message ?: "Error fetching upcoming events")
            }
        }
    }

    override suspend fun getFinishedEvents(): ResponseState<List<ListEventsItem>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getFinishedEvents()
                ResponseState.Success(response.listEvents)
            } catch (e: Exception) {
                ResponseState.Error(e.message ?: "Error fetching finished events")
            }
        }
    }

    override suspend fun searchEvents(keyword: String): ResponseState<List<ListEventsItem>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.searchEvents(keyword)
                ResponseState.Success(response.listEvents)
            } catch (e: Exception) {
                ResponseState.Error(e.message ?: "Error searching events")
            }
        }
    }

    override suspend fun getEventDetail(id: String): ResponseState<Event> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getEventDetail(id)
                ResponseState.Success(response.event!!)
            } catch (e: Exception) {
                ResponseState.Error(e.message ?: "Error fetching event detail")
            }
        }
    }

}

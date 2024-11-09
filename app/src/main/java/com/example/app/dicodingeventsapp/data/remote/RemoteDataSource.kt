package com.example.app.dicodingeventsapp.data.remote

import com.example.app.dicodingeventsapp.data.remote.network.ApiService
import com.example.app.dicodingeventsapp.data.remote.response.DetailEventResponse
import com.example.app.dicodingeventsapp.data.remote.response.EventResponse

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getUpcomingEvents(): EventResponse {
        return apiService.getEvents(status = 1)
    }

    suspend fun getFinishedEvents(): EventResponse {
        return apiService.getEvents(status = 0)
    }

    suspend fun searchEvents(keyword: String): EventResponse {
        return apiService.getEvents(status = -1, keyword = keyword)
    }

    suspend fun getEventDetail(id: String): DetailEventResponse {
        return apiService.getEventDetail(id)
    }

}

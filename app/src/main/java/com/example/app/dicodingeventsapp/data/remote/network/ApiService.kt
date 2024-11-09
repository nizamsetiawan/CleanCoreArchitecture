package com.example.app.dicodingeventsapp.data.remote.network

import com.example.app.dicodingeventsapp.data.remote.response.DetailEventResponse
import com.example.app.dicodingeventsapp.data.remote.response.EventResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("events")
    suspend fun getEvents(
        @Query("active") status: Int? = 1,
        @Query("q") keyword: String? = null
    ): EventResponse

    @GET("events/{id}")
    suspend fun getEventDetail(
        @Path("id") id: String
    ): DetailEventResponse
}

package com.example.app.dicodingeventsapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.app.dicodingeventsapp.data.local.entity.FavoriteEventEntity

interface LocalRepository {

    suspend fun insertFavoriteEvent(event: FavoriteEventEntity)

    suspend fun deleteFavoriteEvent(eventId: String)

    fun getAllFavoriteEvents(): LiveData<List<FavoriteEventEntity>>

    suspend fun checkFavoriteEvent(eventId: String): Boolean
}

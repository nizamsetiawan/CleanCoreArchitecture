package com.example.app.dicodingeventsapp.data.repository

import androidx.lifecycle.LiveData
import com.example.app.dicodingeventsapp.data.local.LocalDataSource
import com.example.app.dicodingeventsapp.data.local.entity.FavoriteEventEntity
import com.example.app.dicodingeventsapp.domain.repository.LocalRepository

class LocalRepositoryImpl(private val localDataSource: LocalDataSource) : LocalRepository {

    override suspend fun insertFavoriteEvent(event: FavoriteEventEntity) {
        localDataSource.insertFavoriteEvent(event)
    }

    override suspend fun deleteFavoriteEvent(eventId: String) {
        localDataSource.deleteFavoriteEvent(eventId)
    }

    override fun getAllFavoriteEvents(): LiveData<List<FavoriteEventEntity>> {
        return localDataSource.getAllFavoriteEvents()
    }

    override suspend fun checkFavoriteEvent(eventId: String): Boolean {
        return localDataSource.checkFavoriteEventById(eventId) > 0
    }
}

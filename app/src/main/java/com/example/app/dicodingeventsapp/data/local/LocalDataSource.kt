package com.example.app.dicodingeventsapp.data.local

import androidx.lifecycle.LiveData
import com.example.app.dicodingeventsapp.data.local.entity.FavoriteEventEntity

class LocalDataSource(private val db: AppDatabase) {
    suspend fun insertFavoriteEvent(favoriteEvent: FavoriteEventEntity) {
        db.favoriteEventDao().insertFavoriteEventEntity(favoriteEvent)
    }

    suspend fun deleteFavoriteEvent(id: String) {
        db.favoriteEventDao().deleteFavoriteEventEntity(id)
    }

    suspend fun checkFavoriteEventById(id: String): Int {
        return db.favoriteEventDao().checkFavoriteEventEntityById(id)
    }

    fun getAllFavoriteEvents(): LiveData<List<FavoriteEventEntity>> {
        return db.favoriteEventDao().getAllFavoriteEventEntity()
    }
}

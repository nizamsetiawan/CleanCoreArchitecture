package com.example.app.dicodingeventsapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.app.dicodingeventsapp.data.local.entity.FavoriteEventEntity

@Dao
interface FavoriteEventDao {

    @Insert
    suspend fun insertFavoriteEventEntity(favoriteEventEntity: FavoriteEventEntity)

    @Query("SELECT * FROM favorite_event")
    fun getAllFavoriteEventEntity(): LiveData<List<FavoriteEventEntity>>

    @Query("SELECT count(*) FROM favorite_event WHERE id = :id")
    suspend fun checkFavoriteEventEntityById(id: String): Int

    @Query("DELETE FROM favorite_event WHERE id = :id")
    suspend fun deleteFavoriteEventEntity(id: String): Int
}

package com.example.app.dicodingeventsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.app.dicodingeventsapp.data.local.dao.FavoriteEventDao
import com.example.app.dicodingeventsapp.data.local.entity.FavoriteEventEntity
import com.example.app.dicodingeventsapp.presentation.utils.Constant.DB_VERSION

@Database(
    entities = [
        FavoriteEventEntity::class
    ], version = DB_VERSION, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteEventDao(): FavoriteEventDao
}
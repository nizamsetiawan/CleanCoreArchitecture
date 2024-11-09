package com.example.app.dicodingeventsapp.di

import androidx.room.Room
import com.example.app.dicodingeventsapp.data.local.AppDatabase
import com.example.app.dicodingeventsapp.presentation.utils.Constant
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val LocalModules = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            Constant.DB_NAME
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
}
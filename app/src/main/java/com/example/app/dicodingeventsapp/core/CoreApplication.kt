package com.example.app.dicodingeventsapp.core

import android.app.Application
import com.example.app.dicodingeventsapp.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class CoreApplication : Application() {
    override fun onCreate() {
        super.onCreate()
//        Prefs.init(this)
        startKoin {
            androidLogger()
            androidContext(this@CoreApplication)
            modules(appModules)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}
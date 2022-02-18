package com.my.apiproject.application

import android.app.Application
import com.my.apiproject.room.database.AppDatabase

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppDatabase.getInstance(this@MainApplication)

    }
}
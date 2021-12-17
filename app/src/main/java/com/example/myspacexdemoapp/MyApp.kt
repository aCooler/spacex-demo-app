package com.example.myspacexdemoapp

import android.app.Application
import com.example.myspacexdemoapp.di.AppComponent
import com.example.myspacexdemoapp.di.DaggerAppComponent

class MyApp : Application() {
    var appComponent: AppComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent =
            DaggerAppComponent.factory().create(applicationContext)
    }
}

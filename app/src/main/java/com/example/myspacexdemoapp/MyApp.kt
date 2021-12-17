package com.example.myspacexdemoapp

import android.app.Application
import com.example.myspacexdemoapp.di.AppComponent
import com.example.myspacexdemoapp.di.AppModule
import com.example.myspacexdemoapp.di.DaggerAppComponent
import com.example.myspacexdemoapp.di.NetModule
import com.example.myspacexdemoapp.di.ViewModelModule

class MyApp : Application() {
    var appComponent: AppComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent =
            DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule())
                .viewModelModule(ViewModelModule())
                .build()
    }
}

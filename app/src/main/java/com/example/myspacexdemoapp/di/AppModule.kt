package com.example.myspacexdemoapp.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(var mApplication: Application) {
    @Provides
    @Singleton
    fun providesContext(): Context {
        return mApplication
    }
}

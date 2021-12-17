package com.example.myspacexdemoapp.di

import com.example.myspacexdemoapp.api.SpaceXApi
import com.example.myspacexdemoapp.ui.launch.LaunchDetailsViewModel
import com.example.myspacexdemoapp.ui.launches.LaunchesViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun provideLaunchesViewModel(spaceXApi: SpaceXApi): LaunchesViewModel {
        return LaunchesViewModel(spaceXApi)
    }

    @Provides
    @Singleton
    fun provideLaunchDetailsViewModel(spaceXApi: SpaceXApi): LaunchDetailsViewModel {
        return LaunchDetailsViewModel(spaceXApi)
    }



}
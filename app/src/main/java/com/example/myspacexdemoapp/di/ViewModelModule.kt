package com.example.myspacexdemoapp.di

import com.example.myspacexdemoapp.ui.launch.LaunchDetailsViewModel
import com.example.myspacexdemoapp.ui.launches.LaunchesViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {
    @Provides
    @Singleton
    fun provideLaunchesViewModel(spaceXApi: com.example.spacexdemoapp.api.SpaceXApi): LaunchesViewModel {
        return LaunchesViewModel(spaceXApi)
    }

    @Provides
    @Singleton
    fun provideLaunchDetailsViewModel(spaceXApi: com.example.spacexdemoapp.api.SpaceXApi): LaunchDetailsViewModel {
        return LaunchDetailsViewModel(spaceXApi)
    }
}

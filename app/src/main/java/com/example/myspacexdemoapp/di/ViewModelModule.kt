package com.example.myspacexdemoapp.di

import com.example.domain.SpaceXRepository
import com.example.myspacexdemoapp.ui.launch.LaunchDetailsViewModel
import com.example.myspacexdemoapp.ui.launches.LaunchesViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule{

    @Provides
    @Singleton
    fun provideLaunchesViewModel(spaceXRepository: SpaceXRepository): LaunchDetailsViewModel  {
        return LaunchDetailsViewModel(spaceXRepository)
    }

    @Provides
    @Singleton
    fun provideLaunchDetailsViewModel(spaceXRepository: SpaceXRepository): LaunchesViewModel {
        return LaunchesViewModel(spaceXRepository)
    }
}

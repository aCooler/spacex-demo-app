package com.example.myspacexdemoapp.di

import com.example.domain.SpaceXRepository
import com.example.domain.SpaceXRepositoryImpl
import com.example.myspacexdemoapp.ui.launch.LaunchDetailsViewModel
import com.example.myspacexdemoapp.ui.launches.LaunchesViewModel
import com.example.spacexdemoapp.api.SpaceXApi
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

    @Provides
    @Singleton
    fun provideSpaceXRespository(spaceXApi: SpaceXApi): SpaceXRepository {
        return SpaceXRepositoryImpl(spaceXApi = spaceXApi)
    }
}

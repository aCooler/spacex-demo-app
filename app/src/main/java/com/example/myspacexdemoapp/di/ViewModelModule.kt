package com.example.myspacexdemoapp.di

import com.example.domain.GetLaunchDetailsUseCase
import com.example.domain.GetLaunchesUseCase
import com.example.myspacexdemoapp.ui.launch.LaunchDetailsViewModel
import com.example.myspacexdemoapp.ui.launches.LaunchesViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun provideLaunchDetailsViewModel(useCase: GetLaunchDetailsUseCase): LaunchDetailsViewModel {
        return LaunchDetailsViewModel(useCase)
    }

    @Provides
    @Singleton
    fun provideLaunchesViewModel(useCase: GetLaunchesUseCase): LaunchesViewModel {
        return LaunchesViewModel(useCase)
    }
}

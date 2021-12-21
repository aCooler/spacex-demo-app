package com.example.myspacexdemoapp.ui.launches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myspacexdemoapp.ui.launch.LaunchDetailsViewModel
import com.example.spacexdemoapp.api.SpaceXApi

class LaunchesViewModelFactory(private val spaceXApi: SpaceXApi) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LaunchesViewModel::class.java)) {
            return LaunchesViewModel(spaceXApi) as T
        }
        if (modelClass.isAssignableFrom(LaunchDetailsViewModel::class.java)) {
            return LaunchDetailsViewModel(spaceXApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

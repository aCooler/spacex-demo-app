package com.example.myspacexdemoapp.ui.launches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.spacexdemoapp.api.SpaceXApi
import com.example.myspacexdemoapp.ui.launch.LaunchDetailsViewModel

class LaunchesViewModelFactory(private val spaceXApi: com.example.spacexdemoapp.api.SpaceXApi) : ViewModelProvider.Factory {

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

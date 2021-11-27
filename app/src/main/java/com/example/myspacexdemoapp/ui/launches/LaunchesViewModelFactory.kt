package com.example.myspacexdemoapp.ui.launches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myspacexdemoapp.api.SpaceXApi
import com.example.myspacexdemoapp.ui.launch.LaunchViewModel

class LaunchesViewModelFactory (private val spaceXApi: SpaceXApi) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LaunchesViewModel::class.java)) {
            return LaunchesViewModel(spaceXApi) as T
        }
        if (modelClass.isAssignableFrom(LaunchViewModel::class.java)) {
            return LaunchViewModel(spaceXApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

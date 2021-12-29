package com.example.myspacexdemoapp.ui.launches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.SpaceXRepository
import com.example.myspacexdemoapp.ui.launch.LaunchDetailsViewModel
import javax.inject.Inject

class LaunchesViewModelFactory @Inject constructor(private val spaceXRepository: SpaceXRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LaunchesViewModel::class.java)) {
            return LaunchesViewModel(spaceXRepository) as T
        }
        if (modelClass.isAssignableFrom(LaunchDetailsViewModel::class.java)) {
            return LaunchDetailsViewModel(spaceXRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

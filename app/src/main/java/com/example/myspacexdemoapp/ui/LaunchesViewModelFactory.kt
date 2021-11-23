package com.example.myspacexdemoapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myspacexdemoapp.api.SpaceXApi

class LaunchesViewModelFactory (private val spaceXApi: SpaceXApi) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LaunchesViewModel::class.java)) {
            return LaunchesViewModel(spaceXApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

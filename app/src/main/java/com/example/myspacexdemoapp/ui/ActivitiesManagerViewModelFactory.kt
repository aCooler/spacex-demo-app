package com.example.myspacexdemoapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ActivitiesManagerViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActivitiesManagerViewModel::class.java)) {
            return ActivitiesManagerViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
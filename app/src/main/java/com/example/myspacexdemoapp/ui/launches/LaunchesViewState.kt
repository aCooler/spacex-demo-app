package com.example.myspacexdemoapp.ui.launches

import com.example.domain.LaunchData

sealed class LaunchesViewState {
    data class Success(val model: List<LaunchData>?) : LaunchesViewState()
    data class Error(val error: Throwable) : LaunchesViewState()
    object Loading : LaunchesViewState()
}

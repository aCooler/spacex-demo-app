package com.example.myspacexdemoapp.ui

sealed class LaunchesViewState {
    data class Success(val model: List<LaunchUiModel>?) : LaunchesViewState()
    data class Error(val error: Throwable) : LaunchesViewState()
    object Loading : LaunchesViewState()
}

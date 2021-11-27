package com.example.myspacexdemoapp.ui.launches

sealed class LaunchesViewState {
    data class Success(val model: List<LaunchUiModel>?): LaunchesViewState()
    data class Error(val error: Throwable): LaunchesViewState()
    object Loading: LaunchesViewState()
}

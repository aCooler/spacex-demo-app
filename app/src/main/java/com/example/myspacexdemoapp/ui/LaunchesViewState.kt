package com.example.myspacexdemoapp.ui

sealed class LaunchesViewState {
    class Success(val model: List<LaunchUiModel>?): LaunchesViewState()
    class Error(val error: Throwable): LaunchesViewState()
}
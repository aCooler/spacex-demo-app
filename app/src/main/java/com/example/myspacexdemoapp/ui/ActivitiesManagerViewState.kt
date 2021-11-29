package com.example.myspacexdemoapp.ui


sealed class ActivitiesManagerViewState {
    data class Details(val id: String) : ActivitiesManagerViewState()
    data class Error(val error: Throwable) : ActivitiesManagerViewState()
    object Main : ActivitiesManagerViewState()
}
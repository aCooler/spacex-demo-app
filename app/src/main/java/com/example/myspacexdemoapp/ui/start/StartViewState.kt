package com.example.myspacexdemoapp.ui.start

import com.example.domain.HomeData

sealed class StartViewState {
    data class Success(val model: HomeData) : StartViewState()
    data class Error(val error: Throwable) : StartViewState()
    object Loading : StartViewState()
}

package com.example.myspacexdemoapp.ui.rocketDetails

import com.example.domain.RocketDetailsData

sealed class RocketDetailsViewState {
    data class Success(val model: RocketDetailsData) : RocketDetailsViewState()
    data class Error(val error: Throwable) : RocketDetailsViewState()
    object Loading : RocketDetailsViewState()
}

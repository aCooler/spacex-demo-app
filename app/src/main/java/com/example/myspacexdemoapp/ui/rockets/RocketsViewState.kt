package com.example.myspacexdemoapp.ui.rockets

import com.example.domain.RocketData

sealed class RocketsViewState {
    data class Success(val model: List<RocketData>?) : RocketsViewState()
    data class Error(val error: Throwable) : RocketsViewState()
    object Loading : RocketsViewState()
}

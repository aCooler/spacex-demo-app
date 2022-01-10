package com.example.myspacexdemoapp.ui.launch

import com.example.domain.LaunchData

sealed class LaunchDetailsViewState {
    data class Success(val model: LaunchData) : LaunchDetailsViewState()
    data class Error(val error: Throwable) : LaunchDetailsViewState()
    object Loading : LaunchDetailsViewState()
}

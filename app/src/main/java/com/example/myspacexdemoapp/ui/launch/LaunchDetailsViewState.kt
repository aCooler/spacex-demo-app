package com.example.myspacexdemoapp.ui.launch

import com.example.myspacexdemoapp.ui.launches.LaunchUiModel

sealed class LaunchDetailsViewState {
    data class Success(val model: LaunchUiModel?) : LaunchDetailsViewState()
    data class Error(val error: Throwable) : LaunchDetailsViewState()
    object Loading : LaunchDetailsViewState()
}

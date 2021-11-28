package com.example.myspacexdemoapp.ui.launch

import com.example.myspacexdemoapp.ui.launches.LaunchUiModel

sealed class LaunchViewState {
    data class Success(val model: LaunchUiModel?): LaunchViewState()
    data class Error(val error: Throwable): LaunchViewState()
    object Loading: LaunchViewState()
}

package com.example.myspacexdemoapp.ui

import com.example.myspacexdemoapp.ui.launches.LaunchUiModel

sealed class DataModel {
    data class Picture(
        val launchUiModel: LaunchUiModel
    ) : DataModel()
    data class Card(
        val launchUiModel: LaunchUiModel
    ) : DataModel()
    data class Details(
        val launchUiModel: LaunchUiModel
    ) : DataModel()
    data class Single(
        val launchUiModel: LaunchUiModel

    ) : DataModel()
    data class Gallery(
        val launchUiModel: LaunchUiModel
    ) : DataModel()
}


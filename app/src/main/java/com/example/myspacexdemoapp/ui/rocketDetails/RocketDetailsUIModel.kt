package com.example.myspacexdemoapp.ui.rocketDetails

sealed class RocketDetailsUIModel {
    data class MainInfo(
        val pictureUrl: String,
        val success: Boolean
    ) : RocketDetailsUIModel()

    data class Details(
        val details: String,
    ) :
        RocketDetailsUIModel()

    data class RowExpandable(
        val word: String,
        val position: Int
    ) :
        RocketDetailsUIModel()

    data class Row(
        val word: String,
        val position: Int
    ) :
        RocketDetailsUIModel()

    data class TitleAndText(
        val title: String,
        val text: String,
        val position: Int
    ) :
        RocketDetailsUIModel()
    data class IconAndText(
        val icon: Int,
        val text: String,
    ) :
        RocketDetailsUIModel()
}

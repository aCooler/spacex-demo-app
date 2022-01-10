package com.example.myspacexdemoapp.ui

sealed class UIModel {
    data class MainInfo(
        val number: String,
        val pictureUrl: String,
        val badgeUrl: String,
        val success: Boolean,
    ) : UIModel()

    data class LaunchEvent(
        val rocket: String,
        val date: String,
        val place: String,
        val reused: Boolean,
    ) :
        UIModel()

    data class Details(
        val details: String,
    ) :
        UIModel()

    data class SingleString(
        val word: String,
    ) :
        UIModel()

    data class TitleAndText(
        val title: String,
        val text: String,
    ) :
        UIModel()

    data class Gallery(val pictures: List<String>) :
        UIModel()
}

data class MainScreenModel(
    val number: String,
    val pictureUrl: String,
    val badgeUrl: String,
    val success: Boolean,
    val launch: String,
    val rocket: String,
    val date: String,
    val place: String,
)

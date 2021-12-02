package com.example.myspacexdemoapp.ui

sealed class DataModel {
    data class Picture(
        val number: String?,
        val pictureUrl: String?,
        val badgeUrl: String?,
        val success: Boolean?
    ) :
        DataModel()

    data class LaunchEvent(
        val rocket: String?,
        val date: String?,
        val place: String?,
        val reused: Boolean?
    ) :
        DataModel()

    data class Details(
        val details: String?,
    ) :
        DataModel()

    data class OneWord(
        val word: String?,
    ) :
        DataModel()

    data class TitleAndText(
        val title: String?,
        val text: String?,
    ) :
        DataModel()

    data class Gallery(val pictures: List<String>?) :
        DataModel()

}

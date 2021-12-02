package com.example.myspacexdemoapp.ui

sealed class DataModel {
    data class Picture(
        var number: String? = null,
        var picture_url: String? = null,
        var badge_url: String? = null,
        var success: Boolean? = null
    ) :
        DataModel()

    data class LaunchEvent(
        var rocket: String? = null,
        var date: String? = null,
        var place: String? = null,
        var reused: Boolean? = null
    ) :
        DataModel()

    data class Details(
        var details: String? = null,
    ) :
        DataModel()

    data class OneWord(
        var word: String? = null,
    ) :
        DataModel()

    data class TitleAndText(
        var title: String? = null,
        var text: String? = null,
    ) :
        DataModel()

    data class Gallery(var pictures: List<String>? = null) :
        DataModel()

}

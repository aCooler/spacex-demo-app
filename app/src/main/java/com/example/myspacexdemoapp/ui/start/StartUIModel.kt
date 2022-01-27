package com.example.myspacexdemoapp.ui.start

sealed class StartUIModel {
    data class Timer(
        val name: String,
        val seconds: String,
        val minutes: String,
        val hours: String,
        val days: String,
    ) : StartUIModel()

    data class Launches(
        val successful: String,
        val total: String,
        val efficiency: String,
        val toLaunches: String,
    ) :
        StartUIModel()

    data class Rockets(
        val tweet: String,
    ) :
        StartUIModel()
}

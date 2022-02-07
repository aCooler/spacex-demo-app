package com.example.domain

data class RocketData(
    val name: String,
    val firstFlight: String,
    val country: String,
    val cost: String,
    val stagesNumber: String,
    val activity: Boolean,
    val rocketPicture: String,
) {
    companion object {
        val EMPTY = RocketData(
            name = "",
            firstFlight = "",
            country = "",
            cost = "",
            stagesNumber = "",
            activity = false,
            rocketPicture = ""
        )
    }
}

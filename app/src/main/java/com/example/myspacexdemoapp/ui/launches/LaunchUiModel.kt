package com.example.myspacexdemoapp.ui.launches


data class LaunchUiModel(
    val number: String = "",
    val badge: String = "",
    val name: String = "",
    val date: String = "",
    val rocketName: String = "",
    val place: String = "",
    val success: Boolean = false,
    val picture: String = "",
    val details: String = "",
    val orbit: String = "",
    val nationality: String = "",
    val manufacturer: String = "",
    val customers: List<String> = emptyList(),
    val mass: Double = 0.0,
    val pictures: List<String> = emptyList(),
    val video: String = "",
    val reused: Boolean = false,
)

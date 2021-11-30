package com.example.myspacexdemoapp.ui.launches

data class LaunchUiModel(
    val number: String = "",
    val mission: Mission? = null,
    val payload: Payload? = null,
    val linkInfo: LinkInfo? = null
)

data class Mission(
    val name: String = "",
    val date: String = "",
    val rocketName: String = "",
    val place: String = "",
    val success: Boolean = true,
    val details: String = "",
)

data class Payload(
    val orbit: String = "",
    val nationality: String = "",
    val manufacturer: String = "",
    val customers: List<String> = emptyList(),
    val mass: Double = 0.0,
    val reused: Boolean = false,
)

data class LinkInfo(
    val badge: String = "",
    val picture: String = "",
    val pictures: List<String> = emptyList(),
    val video: String = "",
)

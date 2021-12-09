package com.example.myspacexdemoapp.ui.launches

data class LaunchUiModel(
    val number: String?,
    val mission: Mission?,
    val payload: Payload?,
    val linkInfo: LinkInfo
)

data class Mission(
    val name: String?,
    val date: String?,
    val rocketName: String?,
    val place: String?,
    val success: Boolean?,
    val details: String?,
)

data class Payload(
    val orbit: String?,
    val nationality: String?,
    val manufacturer: String?,
    val customers: List<String>?,
    val mass: Double?,
    val reused: Boolean?,
)

data class LinkInfo(
    val badge: String,
    val picture: String,
    val pictures: List<String>,
    val video: String = "",
) {

    companion object {

        val EMPTY = LinkInfo(
            badge = "",
            picture = "",
            pictures = emptyList(),
            video = "",
        )
    }
}

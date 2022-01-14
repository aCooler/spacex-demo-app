package com.example.domain

data class LaunchData(
    val number: String,
    val mission: Mission,
    val payload: Payload = Payload.EMPTY,
    val linkInfo: LinkInfo
) {

    companion object {

        val EMPTY = LaunchData(
            number = "",
            mission = Mission.EMPTY,
            payload = Payload.EMPTY,
            linkInfo = LinkInfo.EMPTY,
        )
    }
}

data class Mission(
    val name: String,
    val date: String,
    val rocketName: String,
    val place: String,
    val success: Boolean,
    val details: String,
) {

    companion object {

        val EMPTY = Mission(
            name = "",
            date = "",
            rocketName = "",
            place = "",
            success = false,
            details = "",
        )
    }
}

data class Payload(
    val orbit: String,
    val nationality: String,
    val manufacturer: String,
    val customers: List<String?>,
    val mass: Double,
    val reused: Boolean,
) {

    companion object {
        val EMPTY = Payload(
            orbit = "",
            nationality = "",
            manufacturer = "",
            customers = emptyList(),
            mass = 0.0,
            reused = false,
        )
    }
}

data class LinkInfo(
    val badge: String,
    val picture: String,
    val pictures: List<String?>,
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

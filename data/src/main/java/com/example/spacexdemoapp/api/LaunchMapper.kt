package com.example.spacexdemoapp.api

import com.apollographql.apollo3.api.ApolloResponse
import com.example.domain.LaunchData
import com.example.domain.LinkInfo
import com.example.domain.Mission
import com.example.domain.Payload
import com.example.domain.RocketData
import com.example.domain.toDateString
import com.example.spacexdemoapp.api.retrofit.Rocket
import spacexdemoapp.GetLaunchQuery
import spacexdemoapp.GetLaunchesQuery
import java.text.SimpleDateFormat
import java.util.Locale

fun List<Rocket>.toRockets() = this.map {
    RocketData.EMPTY.copy(
        name = it.name,
        firstFlight = it.firstFlight.toFirstFlight(),
        country = it.country,
        cost = it.costPerLaunch.toString().toCost().toString(),
        stagesNumber = it.stages.toString().toStages().toString(),
        activity = it.active,
        rocketPicture = it.flickrImages.getOrNull(0) ?: ""
    )
}

private fun String.toCost(): CharSequence {
    var cost = this
    "${cost.toInt() / 1000000} Millions Per Launch".also { cost = it }
    return cost
}

private fun CharSequence.toStages(): CharSequence {
    return "$this Stages"
}

private fun String.toFirstFlight(): String {
    return "First Flight ${this.toDateFormat()}"
}

private fun String.toDateFormat(): String {
    val parser = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val formatter = SimpleDateFormat("MMM dd yyyy", Locale.US)
    return formatter.format(parser.parse(this) ?: "")
}


fun ApolloResponse<GetLaunchesQuery.Data>.toLaunches() =
    this.data?.launches?.map {
        LaunchData(
            number = it?.id ?: LaunchData.EMPTY.number,
            mission = it?.toMission() ?: Mission.EMPTY,
            linkInfo = it?.links?.toLinksInfo() ?: LinkInfo.EMPTY,
        )
    } ?: emptyList()

fun ApolloResponse<GetLaunchQuery.Data>.toLaunchDetails(id: String) =
    LaunchData(
        number = id,
        mission = this.data?.launch?.toMission() ?: Mission.EMPTY,
        payload = this.data?.payload?.toPayload() ?: Payload.EMPTY,
        linkInfo = this.data?.launch?.links?.toLinksInfo() ?: LinkInfo.EMPTY
    )

fun GetLaunchesQuery.Links.toLinksInfo() = LinkInfo(
    badge = mission_patch ?: LinkInfo.EMPTY.badge,
    picture = flickr_images?.firstOrNull() ?: LinkInfo.EMPTY.picture,
    pictures = flickr_images ?: LinkInfo.EMPTY.pictures
)

fun GetLaunchQuery.Launch.toMission(): Mission =
    Mission(
        name = missionDetails.mission_name ?: Mission.EMPTY.name,
        date = missionDetails.launch_date_local?.toDateString() ?: "",
        rocketName = rocket?.rocketFields?.rocket_name ?: Mission.EMPTY.rocketName,
        place = launch_site?.site_name_long ?: Mission.EMPTY.place,
        success = missionDetails.launch_success ?: Mission.EMPTY.success,
        details = details ?: Mission.EMPTY.details
    )

fun GetLaunchesQuery.Launch.toMission(): Mission {
    return Mission(
        name = missionDetails.mission_name ?: Mission.EMPTY.name,
        date = missionDetails.launch_date_local?.toDateString() ?: "",
        rocketName = rocket?.rocketFields?.rocket_name
            ?: Mission.EMPTY.rocketName,
        place = launch_site?.site_name_long ?: Mission.EMPTY.place,
        success = missionDetails.launch_success ?: Mission.EMPTY.success,
        details = details ?: Mission.EMPTY.details,
    )
}

fun GetLaunchQuery.Payload.toPayload(): Payload {
    return Payload(
        orbit = orbit ?: Payload.EMPTY.orbit,
        nationality = nationality ?: Payload.EMPTY.nationality,
        manufacturer = manufacturer ?: Payload.EMPTY.manufacturer,
        customers = customers ?: Payload.EMPTY.customers,
        mass = payload_mass_kg ?: Payload.EMPTY.mass,
        reused = reused ?: Payload.EMPTY.reused,
    )
}

fun GetLaunchQuery.Links.toLinksInfo() = with(linkInfo) {
    LinkInfo(
        badge = mission_patch ?: LinkInfo.EMPTY.badge,
        picture = flickr_images?.firstOrNull() ?: LinkInfo.EMPTY.picture,
        pictures = flickr_images ?: LinkInfo.EMPTY.pictures,
        video = video_link ?: LinkInfo.EMPTY.video
    )
}

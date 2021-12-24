package com.example.myspacexdemoapp.ui.mappers

import com.example.myspacexdemoapp.ui.launches.LinkInfo
import com.example.myspacexdemoapp.ui.launches.Mission
import com.example.myspacexdemoapp.ui.launches.Payload
import spacexdemoapp.GetLaunchQuery
import spacexdemoapp.GetLaunchesQuery

fun GetLaunchesQuery.Links.toLinksInfo() = LinkInfo(
    badge = mission_patch ?: LinkInfo.EMPTY.badge,
    picture = when {
        flickr_images.isNullOrEmpty() -> LinkInfo.EMPTY.picture
        else -> flickr_images?.first() ?: LinkInfo.EMPTY.picture
    },
    pictures = flickr_images ?: LinkInfo.EMPTY.pictures
)

fun GetLaunchQuery.Launch.toMission(): Mission =
    Mission(
        name = missionDetails.mission_name ?: Mission.EMPTY.name,
        //date = missionDetails.launch_date_utc?.toString() ?: "",
        date =  "",
        rocketName = rocket?.rocketFields?.rocket_name ?: Mission.EMPTY.rocketName,
        place = launch_site?.site_name_long ?: Mission.EMPTY.place,
        success = missionDetails.launch_success ?: Mission.EMPTY.success,
        details = details ?: Mission.EMPTY.details
    )

fun GetLaunchesQuery.Launch.toMission(): Mission {
    return Mission(
        name = missionDetails.mission_name ?: Mission.EMPTY.name,
        //date = missionDetails.launch_date_utc?.toString() ?: "",
        date =  "",
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
        picture = when {
            flickr_images.isNullOrEmpty() -> LinkInfo.EMPTY.picture
            else -> flickr_images?.first() ?: LinkInfo.EMPTY.picture
        },
        pictures = flickr_images ?: LinkInfo.EMPTY.pictures,
        video = video_link ?: LinkInfo.EMPTY.video
    )
}

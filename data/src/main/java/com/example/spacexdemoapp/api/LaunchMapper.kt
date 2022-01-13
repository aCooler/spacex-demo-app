package com.example.domain

import com.apollographql.apollo3.api.ApolloResponse
import spacexdemoapp.GetLaunchQuery
import spacexdemoapp.GetLaunchesQuery

class LaunchMapper {

    fun toLaunchDetails(id: String, response: ApolloResponse<GetLaunchQuery.Data>): LaunchData {
        val linkInfo = response.data?.launch?.links?.toLinksInfo() ?: LinkInfo.EMPTY
        val payload = response.data?.payload?.toPayload() ?: Payload.EMPTY
        val mission = response.data?.launch?.toMission() ?: Mission.EMPTY
        return LaunchData(
            number = id,
            mission = mission,
            payload = payload,
            linkInfo = linkInfo
        )
    }

    fun toLaunches(response: ApolloResponse<GetLaunchesQuery.Data>): List<LaunchData> {
        return response.data?.launches?.map {
            LaunchData(
                number = it?.id ?: LaunchData.EMPTY.number,
                mission = it?.toMission() ?: Mission.EMPTY,
                linkInfo = it?.links?.toLinksInfo() ?: LinkInfo.EMPTY,
            )
        } ?: emptyList()
    }
}

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
        picture = when {
            flickr_images.isNullOrEmpty() -> LinkInfo.EMPTY.picture
            else -> flickr_images?.first() ?: LinkInfo.EMPTY.picture
        },
        pictures = flickr_images ?: LinkInfo.EMPTY.pictures,
        video = video_link ?: LinkInfo.EMPTY.video
    )
}

package com.example.myspacexdemoapp.ui.mappers

import com.apollographql.apollo.api.Response
import com.example.myspacexdemoapp.ui.launches.LinkInfo
import com.example.myspacexdemoapp.ui.launches.Mission
import com.example.myspacexdemoapp.ui.launches.Payload
import com.example.spacexdemoapp.GetLaunchQuery
import com.example.spacexdemoapp.GetLaunchesQuery

fun GetLaunchesQuery.Links.toLinksInfo() = LinkInfo(
    badge = mission_patch() ?: "",
    picture = when {
        flickr_images().isNullOrEmpty() -> ""
        else -> flickr_images()?.first() ?: ""
    },
    pictures = flickr_images() ?: emptyList()
)

fun GetLaunchesQuery.Launch.toMission(): Mission {
    with(this) {
        return Mission(
            name = fragments().missionDetails().mission_name() ?: Mission.EMPTY.name,
            date = fragments().missionDetails().launch_date_utc().toString(),
            rocketName = rocket()?.fragments()?.rocketFields()?.rocket_name()
                ?: Mission.EMPTY.rocketName,
            place = launch_site()?.site_name_long() ?: Mission.EMPTY.place,
            success = fragments().missionDetails().launch_success() ?: Mission.EMPTY.success,
            details = Mission.EMPTY.details,
        )
    }
}
fun toMission(response: Response<GetLaunchQuery.Data>?): Mission =
    with(response?.data) {
        return Mission(
            name = this?.launch()?.fragments()?.missionDetails()?.mission_name() ?: "",
            date = this?.launch()?.fragments()?.missionDetails()
                ?.launch_date_utc().toString(),
            rocketName = this?.launch()?.rocket()?.fragments()?.rocketFields()
                ?.rocket_name() ?: "",
            place = this?.launch()?.launch_site()?.site_name_long() ?: "",
            success = this?.launch()?.fragments()?.missionDetails()?.launch_success() ?: true,
            details = this?.launch()?.details() ?: ""
        )
    }
fun toPayload(response: Response<GetLaunchQuery.Data>): Payload {
    with(response.data){
    return Payload(
        orbit = this?.payload()?.orbit() ?: "",
        nationality = this?.payload()?.nationality() ?: "",
        manufacturer = this?.payload()?.manufacturer() ?: "",
        customers = this?.payload()?.customers() ?: emptyList(),
        mass = this?.payload()?.payload_mass_kg() ?: 0.0,
        reused = this?.payload()?.reused() ?: false,
    )}
}


fun GetLaunchQuery.Links.toLinksInfo() = with(fragments().linkInfo()) {
    LinkInfo(
        badge = mission_patch() ?: "",
        picture = when {
            flickr_images().isNullOrEmpty() -> ""
            else -> flickr_images()?.first() ?: ""
        },
        pictures = flickr_images() ?: emptyList(),
        video = video_link() ?: ""
    )
}
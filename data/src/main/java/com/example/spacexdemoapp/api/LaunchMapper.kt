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

class LaunchMapper {

private fun String.toCost(): CharSequence {
    var cost = this
    "${(cost.toInt() / 1000000)} Millions Per Launch".also { cost = it }
    return cost
}

private fun CharSequence.toStages(): CharSequence {
    return "$this Stages"
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

    fun toRocketDetails(name: String, response: List<Rocket>): RocketDetailsData {
        val rocket = response.first { it.name == name }
        return RocketDetailsData(
            number = name,
            mission = mission(rocket),
            linkInfo = LinkInfo.EMPTY.copy(picture = rocket.flickrImages.first()),
            payload = Payload.EMPTY,
            basics = basicInfoEntity(rocket),
            expandables = mutableListOf(
                Expandable(
                    topItem = RocketSingleValue(word = "Characteristics"),
                    itemList = mutableListOf(
                        TitleAndTextList(
                            topItem = RocketSingleValue.EMPTY,
                            itemList = mapOf(
                                "Height" to rocket.height.meters.toString(),
                                "Mass" to rocket.mass.kg.toString(),
                                "Side Boosters" to rocket.boosters.toString(),
                            )
                        ),
                    )
                ),
                Expandable(
                    topItem = RocketSingleValue(word = "Propulsion"),
                    itemList = mutableListOf(
                        TitleAndTextList(
                            topItem = RocketSingleValue.EMPTY,
                            itemList = mapOf(
                                "Engine Count" to rocket.mass.kg.toString(),
                                "TWR" to rocket.boosters.toString(),
                                "Sea Level Thrust" to rocket.engines.thrustSeaLevel.kN.toString(),
                                "Vacuum Thrust" to rocket.engines.thrustVacuum.kN.toString(),
                                "ISP (Sea/Vacuum)" to rocket.engines.isp.seaLevel.toString(),
                                "Propellant #1" to (rocket.engines.propellant1),
                                "Propellant #2" to (rocket.engines.propellant2),
                                "Max Engine Loss" to rocket.engines.engineLossMax.toString()

                            )
                        ),
                    )
                ),
                expandable(rocket),
            )
        )
    }

    private fun expandable(rocket: Rocket) = Expandable(
        topItem = RocketSingleValue(word = "Stages"),
        itemList = mutableListOf(
            TitleAndTextList(
                topItem = RocketSingleValue(word = "STAGE 1"),
                itemList = mapOf(
                    "Engine Burn Time" to rocket.firstStage.burnTimeSec.toString(),
                    "Engine Count" to rocket.firstStage.engines.toString(),
                    "Fuel Capacity" to rocket.firstStage.fuelAmountTons.toString(),
                    "Reusable" to if (rocket.firstStage.reusable) "Yes" else "Not",
                    "Sea Level Thrust" to rocket.firstStage.thrustSeaLevel.kN.toString(),
                    "Vacuum Thrust" to rocket.firstStage.thrustVacuum.kN.toString(),
                )
            ),
            TitleAndTextList(
                topItem = RocketSingleValue(word = "STAGE 2"),
                itemList = mapOf(
                    "Engine Burn Time" to rocket.secondStage.burnTimeSec.toString(),
                    "Engine Count" to rocket.secondStage.engines.toString(),
                    "Fuel Capacity" to rocket.secondStage.fuelAmountTons.toString(),
                    "Reusable" to if (rocket.secondStage.reusable) "Yes" else "Not",
                    "Sea Level Thrust" to rocket.secondStage.thrust.kN.toString(),
                    "Max Payload" to rocket.secondStage.fuelAmountTons.toString()
                )
            ),
        )
    )

    private fun basicInfoEntity(rocket: Rocket) =
        BasicInfoEntity(
            firstFlight = RocketSingleValue(word = rocket.firstFlight),
            country = RocketSingleValue(word = rocket.country),
            stages = RocketSingleValue(word = rocket.stages.toString()),
            cost = RocketSingleValue(word = rocket.costPerLaunch.toString()),
        )

    private fun mission(rocket: Rocket) = Mission.EMPTY.copy(
        success = rocket.active,
        details = rocket.description
    )

    fun toRockets(it: List<Rocket>?): List<RocketData> {
        return it?.map {
            RocketData.EMPTY.copy(
                name = it.name,
                firstFlight = it.firstFlight,
                country = it.country,
                cost = it.costPerLaunch.toString(),
                stagesNumber = it.stages.toString(),
                activity = it.active,
                rocketPicture = it.flickrImages[0]
            )
        } ?: mutableListOf(RocketData.EMPTY)
    }
}

fun GetLaunchesQuery.Links.toLinksInfo() = LinkInfo(
    badge = mission_patch ?: LinkInfo.EMPTY.badge,
    picture = when {
        flickr_images.isNullOrEmpty() -> LinkInfo.EMPTY.picture
        else -> flickr_images.first() ?: LinkInfo.EMPTY.picture
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
            else -> flickr_images.first() ?: LinkInfo.EMPTY.picture
        },
        pictures = flickr_images ?: LinkInfo.EMPTY.pictures,
        video = video_link ?: LinkInfo.EMPTY.video
    )
}

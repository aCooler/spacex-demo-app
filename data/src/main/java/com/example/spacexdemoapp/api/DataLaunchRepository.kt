package com.example.spacexdemoapp.api

import com.example.domain.LaunchData
import com.example.domain.LaunchRepository
import com.example.domain.RocketData
import com.example.domain.RocketDetailsData
import com.example.spacexdemoapp.api.retrofit.RocketsService
import io.reactivex.rxjava3.core.Flowable

class DataLaunchRepository(
    private val spaceXApi: SpaceXApi,
    private val rocketsService: RocketsService,
) : LaunchRepository {

    override fun getLaunches(): Flowable<List<LaunchData>> {
        return spaceXApi.getLaunches().map {
            it.toLaunches()
        }
    }

    override fun getLaunchById(id: String, payloadId: String): Flowable<LaunchData> {
        return spaceXApi.getLaunchById(id = id, payloadId = payloadId).map {
            it.toLaunchDetails(id)
        }
    }

    override fun getRocketByName(id: String, payloadId: String): Flowable<RocketDetailsData> {
        return rocketsService.listRockets().map {
            it.toRocketDetails(id)
        }
    }

    override fun getRockets(): Flowable<List<RocketData>> {
        return rocketsService.listRockets().map {
            it.toRockets()
        }
    }
}

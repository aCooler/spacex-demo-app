package com.example.data

import com.example.domain.HomeData
import com.example.domain.LaunchData
import com.example.domain.LaunchRepository
import com.example.domain.RocketData
import com.example.spacexdemoapp.api.SpaceXApi
import com.example.spacexdemoapp.api.retrofit.RocketsService
import com.example.spacexdemoapp.api.toLaunchDetails
import com.example.spacexdemoapp.api.toLaunches
import com.example.spacexdemoapp.api.toNextLaunch
import com.example.spacexdemoapp.api.toRockets
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

    override fun getNextLaunch(): Flowable<HomeData> {
        return spaceXApi.getNextLaunch().map {
            it.toNextLaunch()
        }
    }

    override fun getRockets(): Flowable<List<RocketData>> {
        return rocketsService.listRockets().map {
            it.toRockets()
        }
    }
}

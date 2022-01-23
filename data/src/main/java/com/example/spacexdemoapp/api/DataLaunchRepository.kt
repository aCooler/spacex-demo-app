package com.example.domain

import com.example.spacexdemoapp.api.LaunchMapper
import com.example.spacexdemoapp.api.SpaceXApi
import com.example.spacexdemoapp.api.retrofit.RocketsService
import io.reactivex.rxjava3.core.Flowable

class DataLaunchRepository(private val spaceXApi: SpaceXApi, private val rocketsService: RocketsService) : LaunchRepository {

    override fun getLaunches(): Flowable<List<LaunchData>> {
        return spaceXApi.getLaunches().flatMap {
            Flowable.just(LaunchMapper().toLaunches(response = it))
        }
    }

    override fun getLaunchById(id: String, payloadId: String): Flowable<LaunchData> {
        return spaceXApi.getLaunchById(id = id, payloadId = payloadId).flatMap {
            Flowable.just(
                LaunchMapper().toLaunchDetails(id = id, response = it)
            )
        }
    }

    override fun getRockets(): Flowable<List<RocketData>> {
        return rocketsService.listRockets().flatMap {
            Flowable.just(
                LaunchMapper().toRockets(it)
            )
        }
    }
}

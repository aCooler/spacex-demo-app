package com.example.domain

import com.example.spacexdemoapp.api.SpaceXApi
import io.reactivex.rxjava3.core.Flowable

class DataLaunchRepository(private val spaceXApi: SpaceXApi) : LaunchRepository {

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
}

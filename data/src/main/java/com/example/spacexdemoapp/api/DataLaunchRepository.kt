package com.example.spacexdemoapp.api

import com.example.domain.HomeData
import com.example.domain.LaunchData
import com.example.domain.LaunchRepository
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

    override fun getNextLaunch(): Flowable<HomeData> {
        return spaceXApi.getNextLaunch().flatMap {
            Flowable.just(
                LaunchMapper().toNextLaunch(response = it)
            )
        }
    }

}

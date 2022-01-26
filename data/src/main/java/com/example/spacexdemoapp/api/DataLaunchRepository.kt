package com.example.data

import com.example.domain.LaunchData
import com.example.domain.LaunchRepository
import com.example.spacexdemoapp.api.SpaceXApi
import com.example.spacexdemoapp.api.toLaunchDetails
import com.example.spacexdemoapp.api.toLaunches
import com.example.domain.HomeData
import com.example.domain.LaunchData
import com.example.domain.LaunchRepository
import io.reactivex.rxjava3.core.Flowable

class DataLaunchRepository(private val spaceXApi: SpaceXApi) : LaunchRepository {

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
        return spaceXApi.getNextLaunch().flatMap {
            Flowable.just(
                LaunchMapper().toNextLaunch(response = it)
            )
        }
    }

}

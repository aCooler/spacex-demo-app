package com.example.domain

import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class GetLaunchesUseCase @Inject constructor(private val spaceXApi: LaunchRepository) {

    @Inject
    lateinit var mapper: LaunchMapper

    fun invoke(): Flowable<List<LaunchData>> {
        return spaceXApi.getLaunches().flatMap {
            Flowable.just(
                mapper.toLaunches(response = it)
            )
        }
    }
}

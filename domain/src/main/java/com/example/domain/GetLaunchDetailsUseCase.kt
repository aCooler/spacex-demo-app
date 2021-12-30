package com.example.domain

import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class GetLaunchDetailsUseCase @Inject constructor(private val spaceXApi: LaunchRepository) {

    @Inject
    lateinit var mapper: LaunchMapper

    fun invoke(id: String): Flowable<LaunchData> {
        return spaceXApi.getLaunchById(id = id).flatMap {
            Flowable.just(
                mapper.toLaunchDetails(id = id, response = it)
            )
        }
    }
}

package com.example.domain

import io.reactivex.rxjava3.core.Flowable

class GetLaunchDetailsUseCase(private val spaceXApi: LaunchRepository) {

    fun invoke(id: String, payloadId: String): Flowable<LaunchData> {
        return spaceXApi.getLaunchById(id, payloadId)
    }
}

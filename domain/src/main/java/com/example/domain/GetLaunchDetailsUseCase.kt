package com.example.domain

import io.reactivex.rxjava3.core.Flowable

class GetLaunchDetailsUseCase(private val launchRepository: LaunchRepository) {

    fun invoke(id: String, payloadId: String): Flowable<LaunchData> {
        return launchRepository.getLaunchById(id, payloadId)
    }
}

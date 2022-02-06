package com.example.domain

import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class RocketDetailsUseCase @Inject constructor(private val launchRepository: LaunchRepository) {

    fun invoke(id: String, payloadId: String): Flowable<RocketDetailsData> {
        return launchRepository.getRocketByName(id, payloadId)
    }
}

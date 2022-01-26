package com.example.domain

import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class GetLaunchesUseCase @Inject constructor(private val launchRepository: LaunchRepository) {

    fun invoke(): Flowable<List<LaunchData>> {
        return launchRepository.getLaunches()
    }
}

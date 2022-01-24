package com.example.domain

import io.reactivex.rxjava3.core.Flowable

class GetLaunchesUseCase(private val launchRepository: LaunchRepository) {

    fun invoke(): Flowable<List<LaunchData>> {
        return launchRepository.getLaunches()
    }
}

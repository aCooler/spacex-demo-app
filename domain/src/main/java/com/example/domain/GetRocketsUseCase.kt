package com.example.domain

import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class GetRocketsUseCase @Inject constructor(private val launchRepository: LaunchRepository) {

    fun invoke(): Flowable<List<RocketData>> {
        return launchRepository.getRockets()
    }
}

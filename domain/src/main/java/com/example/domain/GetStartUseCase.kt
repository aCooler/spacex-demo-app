package com.example.domain

import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class GetStartUseCase @Inject constructor(private val spaceXApi: LaunchRepository) {

    fun invoke(): Flowable<HomeData> {
        return spaceXApi.getNextLaunch()
    }
}
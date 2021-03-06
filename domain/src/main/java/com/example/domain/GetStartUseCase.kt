package com.example.domain

import io.reactivex.rxjava3.core.Flowable
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetStartUseCase @Inject constructor(private val launchRepository: LaunchRepository) {

    fun invoke(): Flowable<HomeData> {
        val timer = Flowable.interval(0, 1000, TimeUnit.MILLISECONDS)
        val request = launchRepository.getNextLaunch()
        return Flowable.combineLatest(
            timer,
            request
        ) { _: Long, homeData: HomeData ->
            val time = homeData.launchData.mission.date.time - Date().time
            homeData.copy(
                launchData = homeData.launchData
                    .copy(
                        mission = homeData.launchData.mission
                            .copy(
                                date = Date(time)
                            )
                    )
            )
        }
    }
}

package com.example.domain

import io.reactivex.rxjava3.core.Flowable
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetStartUseCase @Inject constructor(private val spaceXApi: LaunchRepository) {

    fun invoke(): Flowable<HomeData> {
        val timer = Flowable.interval(0, 1000, TimeUnit.MILLISECONDS)
        val request = spaceXApi.getNextLaunch()
        return Flowable.combineLatest(
            timer,
            request
        ) { _: Long, homeData: HomeData ->
            homeData.copy(
                launchData = homeData.launchData
                    .copy(
                        mission = homeData.launchData.mission
                            .copy(
                                date = Date
                                    (
                                    homeData.launchData.mission.date.time - Date().time)
                            )
                    )
            )
        }
    }
}

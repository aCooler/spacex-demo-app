package com.example.domain

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class GetStartUseCase @Inject constructor(private val spaceXApi: LaunchRepository) {

    fun invoke(): Flowable<HomeData> {
        Flowable.interval(1, 1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                /*timeToLaunch.seconds--
                dataset[0] = StartUIModel.Timer(
                    name = nextLaunch.mission.name,
                    days = timeToLaunch.day.toString(),
                    hours = timeToLaunch.hours.toString(),
                    minutes = timeToLaunch.minutes.toString(),
                    seconds = timeToLaunch.seconds.toString()
                )
                adapter.setItems(dataset)*/

            }, {
                Log.d("tag", it.message.toString())
            })
        return spaceXApi.getNextLaunch()
    }
}
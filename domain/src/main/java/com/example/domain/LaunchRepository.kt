package com.example.domain

import io.reactivex.rxjava3.core.Flowable

interface LaunchRepository {
    fun getLaunches(): Flowable<List<LaunchData>>
    fun getLaunchById(id: String, payloadId: String): Flowable<LaunchData>
    fun getNextLaunch(): Flowable<HomeData>

}

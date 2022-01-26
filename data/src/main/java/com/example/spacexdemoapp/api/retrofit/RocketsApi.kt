package com.example.spacexdemoapp.data

import com.example.example.Rocket
import com.example.spacexdemoapp.api.retrofit.RocketsService
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class RocketsApi @Inject constructor(private val rocketsService: RocketsService) : IRocketsApi {

    override fun getRockets(): Flowable<List<Rocket>> {
        return rocketsService.listRockets()
    }
}
interface IRocketsApi {
    fun getRockets(): Flowable<List<Rocket>>
}

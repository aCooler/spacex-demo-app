package com.example.spacexdemoapp.api.retrofit

import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET

interface RocketsService {
    @GET("rockets")
    fun listRockets(): Flowable<List<Rocket>>
}
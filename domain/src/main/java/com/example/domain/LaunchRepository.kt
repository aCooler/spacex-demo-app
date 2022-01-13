package com.example.domain

import com.apollographql.apollo3.api.ApolloResponse
import io.reactivex.rxjava3.core.Flowable
import spacexdemoapp.GetLaunchQuery
import spacexdemoapp.GetLaunchesQuery

interface LaunchRepository {
    fun getLaunches(): Flowable<ApolloResponse<GetLaunchesQuery.Data>>
    fun getLaunchById(id: String): Flowable<ApolloResponse<GetLaunchQuery.Data>>
}

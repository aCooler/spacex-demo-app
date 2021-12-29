package com.example.domain

import com.apollographql.apollo3.api.ApolloResponse
import com.example.spacexdemoapp.api.SpaceXApi
import io.reactivex.rxjava3.core.Flowable
import spacexdemoapp.GetLaunchQuery
import spacexdemoapp.GetLaunchesQuery

class LaunchRepositoryImpl(private val spaceXApi: SpaceXApi) : LaunchRepository {

    override fun getLaunches(): Flowable<ApolloResponse<GetLaunchesQuery.Data>> {
        return spaceXApi.getLaunches()
    }

    override fun getLaunchById(id: String): Flowable<ApolloResponse<GetLaunchQuery.Data>> {
        return spaceXApi.getLaunchById(id = id)
    }
}

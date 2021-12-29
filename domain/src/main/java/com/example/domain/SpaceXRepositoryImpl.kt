package com.example.domain

import com.apollographql.apollo3.api.ApolloResponse
import com.example.spacexdemoapp.api.SpaceXApi
import io.reactivex.rxjava3.core.Flowable
import spacexdemoapp.GetLaunchQuery
import spacexdemoapp.GetLaunchesQuery
import javax.inject.Inject

class SpaceXRepositoryImpl @Inject constructor(private val spaceXApi: SpaceXApi) : SpaceXRepository {

    override suspend fun getLaunches(): Flowable<ApolloResponse<GetLaunchesQuery.Data>> {
        return spaceXApi.getLaunches()
    }


    override suspend fun getLaunchById(id: String): Flowable<ApolloResponse<GetLaunchQuery.Data>> {
        return spaceXApi.getLaunchById(id = id)
    }
}
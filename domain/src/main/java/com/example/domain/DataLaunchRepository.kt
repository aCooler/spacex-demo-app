package com.example.domain

import com.apollographql.apollo3.api.ApolloResponse
import com.example.spacexdemoapp.api.SpaceXApi
import kotlinx.coroutines.flow.Flow
import spacexdemoapp.GetLaunchQuery
import spacexdemoapp.GetLaunchesQuery

class DataLaunchRepository(private val spaceXApi: SpaceXApi) : LaunchRepository {

    override suspend fun getLaunches(): Flow<ApolloResponse<GetLaunchesQuery.Data>> {
        return spaceXApi.getLaunches()
    }

    override suspend fun getLaunchById(id: String): Flow<ApolloResponse<GetLaunchQuery.Data>> {
        return spaceXApi.getLaunchById(id = id)
    }
}

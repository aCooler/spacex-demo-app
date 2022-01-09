package com.example.domain

import com.apollographql.apollo3.api.ApolloResponse
import kotlinx.coroutines.flow.Flow
import spacexdemoapp.GetLaunchQuery
import spacexdemoapp.GetLaunchesQuery


interface LaunchRepository {
    suspend fun getLaunches(): Flow<ApolloResponse<GetLaunchesQuery.Data>>
    suspend fun getLaunchById(id: String): Flow<ApolloResponse<GetLaunchQuery.Data>>
}

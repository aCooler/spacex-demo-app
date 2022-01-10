package com.example.spacexdemoapp.api

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import kotlinx.coroutines.flow.Flow
import spacexdemoapp.GetLaunchQuery
import spacexdemoapp.GetLaunchesQuery

class SpaceXApi(private val apolloClient: ApolloClient) : ISpaceXApi {

    override fun getLaunches(): Flow<ApolloResponse<GetLaunchesQuery.Data>> {
        val query = GetLaunchesQuery()
        return apolloClient.query(query).toFlow()
    }

    override fun getLaunchById(id: String): Flow<ApolloResponse<GetLaunchQuery.Data>> {
        val query = GetLaunchQuery(id)
        return apolloClient.query(query).toFlow()
    }
}

interface ISpaceXApi {
    fun getLaunches(): Flow<ApolloResponse<GetLaunchesQuery.Data>>
    fun getLaunchById(id: String): Flow<ApolloResponse<GetLaunchQuery.Data>>
}

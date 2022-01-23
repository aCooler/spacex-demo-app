package com.example.spacexdemoapp.api

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.rx3.rxFlowable
import io.reactivex.rxjava3.core.Flowable
import spacexdemoapp.GetLaunchQuery
import spacexdemoapp.GetLaunchesQuery

class SpaceXApi(private val apolloClient: ApolloClient) : ISpaceXApi {

    override fun getLaunches(): Flowable<ApolloResponse<GetLaunchesQuery.Data>> {
        val query = GetLaunchesQuery()
        return apolloClient.query(query).rxFlowable()
    }

    override fun getLaunchById(
        id: String,
        payloadId: String,
    ): Flowable<ApolloResponse<GetLaunchQuery.Data>> {
        val query = GetLaunchQuery(id, payloadId)
        return apolloClient.query(query).rxFlowable()
    }

}

interface ISpaceXApi {
    fun getLaunches(): Flowable<ApolloResponse<GetLaunchesQuery.Data>>
    fun getLaunchById(id: String, payloadId: String): Flowable<ApolloResponse<GetLaunchQuery.Data>>
}

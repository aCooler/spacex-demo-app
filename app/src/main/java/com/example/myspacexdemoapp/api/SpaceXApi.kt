package com.example.myspacexdemoapp.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.example.spacexdemoapp.GetLaunchQuery
import com.example.spacexdemoapp.GetLaunchesQuery
import io.reactivex.rxjava3.core.Observable

class SpaceXApi(private val apolloClient: ApolloClient) : ISpaceXApi {

    override fun getLaunches(): Observable<Response<GetLaunchesQuery.Data>> {
        val query = GetLaunchesQuery()
        return apolloClient.rxQuery(query)
    }

    override fun getLaunchById(id: String): Observable<Response<GetLaunchQuery.Data>> {
        val query = GetLaunchQuery(id)
        return apolloClient.rxQuery(query)
    }
}
interface ISpaceXApi {
    fun getLaunches(): Observable<Response<GetLaunchesQuery.Data>>
    fun getLaunchById(id: String): Observable<Response<GetLaunchQuery.Data>>
}
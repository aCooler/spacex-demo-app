package com.example.spacex_demo_app.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.example.spacex_demo_app.GetLaunchQuery
import com.example.spacex_demo_app.GetLaunchesQuery
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class SpaceXApi(private val apolloClient: ApolloClient) : ISpaceXApi {

    override fun getLaunches(): Observable<Response<GetLaunchesQuery.Data>> {
        val query = GetLaunchesQuery()
        return apolloClient.rxQuery(query).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getLaunchById(id: String): Observable<Response<GetLaunchQuery.Data>> {
        val query = GetLaunchQuery(id)
        return apolloClient.rxQuery(query).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
interface ISpaceXApi {
    fun getLaunches(): Observable<Response<GetLaunchesQuery.Data>>
    fun getLaunchById(id: String): Observable<Response<GetLaunchQuery.Data>>
}

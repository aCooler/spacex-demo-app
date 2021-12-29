package com.example.domain

import com.apollographql.apollo3.api.ApolloResponse
import io.reactivex.rxjava3.core.Flowable
import spacexdemoapp.GetLaunchesQuery
import javax.inject.Inject

class GetLaunchesUseCase @Inject constructor(private val spaceXApi: LaunchRepository) {
    fun invoke(): Flowable<ApolloResponse<GetLaunchesQuery.Data>> {
        return spaceXApi.getLaunches()
    }
}

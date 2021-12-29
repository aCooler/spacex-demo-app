package com.example.domain

import com.apollographql.apollo3.api.ApolloResponse
import io.reactivex.rxjava3.core.Flowable
import spacexdemoapp.GetLaunchQuery
import javax.inject.Inject

class GetLaunchDetailsUseCase @Inject constructor(private val spaceXApi: SpaceXRepository) {
    suspend fun invoke(id: String): Flowable<ApolloResponse<GetLaunchQuery.Data>> {
        return spaceXApi.getLaunchById(id = id)
    }
}
package com.example.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetLaunchesUseCase @Inject constructor(private val spaceXApi: LaunchRepository) {

    @Inject
    lateinit var mapper: LaunchMapper

    suspend fun invoke(): Flow<List<LaunchData>> =
        spaceXApi.getLaunches().map {
            mapper.toLaunches(it)
        }
}

package com.example.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetLaunchDetailsUseCase @Inject constructor(private val spaceXApi: LaunchRepository) {

    @Inject
    lateinit var mapper: LaunchMapper

    suspend fun invoke(id: String): Flow<LaunchData> {
        return spaceXApi.getLaunchById(id = id).map {
            mapper.toLaunchDetails(id, response = it)
        }
    }
}

package com.example.myspacexdemoapp.data

import com.apollographql.apollo3.api.ApolloResponse
import com.example.spacexdemoapp.api.toLaunches
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.mockito.Answers
import org.mockito.Mockito
import spacexdemoapp.GetLaunchesQuery
import java.util.UUID

class GetLaunchDetailsUseCaseTest {

    @get:Rule
    val instantExecutorRule = androidx.arch.core.executor.testing.InstantTaskExecutorRule()

    @Test
    operator fun invoke() {
        Mockito.mock(ApolloResponse::class.java)
        val mockData =
            Mockito.mock(GetLaunchesQuery.Data::class.java)
        val mockLaunch = getLaunches()
        Mockito.`when`(mockData.launches).thenReturn(
            mockLaunch
        )
        val mockResponse =
            ApolloResponse.Builder(
                operation = GetLaunchesQuery(),
                requestUuid = UUID.randomUUID(),
                data = mockData
            ).build()
        val mapped = mockResponse.toLaunches()
        TestCase.assertEquals(mapped[0].number, "1111")
        TestCase.assertEquals(mapped[0].mission.details, "My details")
        TestCase.assertEquals(mapped[0].mission.name, "My mission name")
    }

    private fun getLaunches(): List<GetLaunchesQuery.Launch> {
        val mockLaunch =
            Mockito.mock(GetLaunchesQuery.Launch::class.java, Answers.RETURNS_DEEP_STUBS)
        mockLaunch.apply {
            Mockito.`when`(mockLaunch.id).thenReturn("1111")
            Mockito.`when`(mockLaunch.details).thenReturn("My details")
            Mockito.`when`(
                mockLaunch.missionDetails.mission_name
            ).thenReturn("My mission name")
        }
        return listOf(mockLaunch)
    }
}

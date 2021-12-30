package com.example.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.apollographql.apollo3.api.ApolloResponse
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Answers
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import spacexdemoapp.GetLaunchQuery
import java.util.UUID

@RunWith(MockitoJUnitRunner::class)
class GetLaunchDetailsUseCaseTest : TestCase() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    operator fun invoke() {
        mock(ApolloResponse::class.java)
        val mockData =
            mock(GetLaunchQuery.Data::class.java)
        val mockLaunch = getLaunch()
        `when`(mockData.launch).thenReturn(
            mockLaunch
        )
        val mockResponse =
            ApolloResponse.Builder(
                operation = GetLaunchQuery("9"),
                requestUuid = UUID.randomUUID(),
                data = mockData
            ).build()
        val mapped = LaunchMapper().toLaunchDetails("9", mockResponse)
        assertEquals(mapped.mission.rocketName, "AC")
        assertEquals(mapped.mission.details, "My details")
        assertEquals(mapped.mission.name, "My mission name")
    }

    private fun getLaunch(): GetLaunchQuery.Launch {
        val mockLaunch =
            mock(GetLaunchQuery.Launch::class.java, Answers.RETURNS_DEEP_STUBS)
        mockLaunch.apply {
            `when`(rocket?.rocketFields?.rocket_name).thenReturn("AC")
            `when`(details).thenReturn("My details")
            `when`(
                missionDetails.mission_name
            ).thenReturn("My mission name")
        }
        return mockLaunch
    }
}

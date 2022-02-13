package com.example.myspacexdemoapp.data

import com.apollographql.apollo3.api.ApolloResponse
import com.example.spacexdemoapp.api.retrofit.Rocket
import com.example.spacexdemoapp.api.toLaunchDetails
import com.example.spacexdemoapp.api.toLaunches
import com.example.spacexdemoapp.api.toRockets
import com.example.spacexdemoapp.api.toNextLaunch
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Answers
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import spacexdemoapp.GetLaunchQuery
import spacexdemoapp.GetLaunchesQuery
import spacexdemoapp.GetNextLaunchQuery
import java.util.Date
import java.util.UUID

@RunWith(MockitoJUnitRunner::class)
class LaunchRepositoryTest : TestCase() {

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
        assertEquals(mapped[0].number, "1111")
        assertEquals(mapped[0].mission.details, "My details")
        assertEquals(mapped[0].mission.name, "My mission name")
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

    @Test
    fun invokeLaunch() {
        val mockLaunch = getLaunch()
        val mockResponse =
            ApolloResponse.Builder(
                operation = GetLaunchQuery("9", "CRS-1"),
                requestUuid = UUID.randomUUID(),
                data = mockLaunch
            ).build()
        val mapped = mockResponse.toLaunchDetails("1111")
        assertEquals(mapped.number, "1111")
        assertEquals(mapped.mission.details, "My details")
        assertEquals(mapped.mission.name, "My mission name")
    }

    private fun getLaunch(): GetLaunchQuery.Data {
        val mockLaunch =
            Mockito.mock(GetLaunchQuery.Data::class.java, Answers.RETURNS_DEEP_STUBS)
        mockLaunch.apply {
            Mockito.`when`(mockLaunch.launch?.details).thenReturn("My details")
            Mockito.`when`(
                mockLaunch.launch?.missionDetails?.mission_name
            ).thenReturn("My mission name")
        }
        return mockLaunch
    }

    @Test
    fun getRockets() {
        val mockRocket =
            Mockito.mock(Rocket::class.java, Answers.RETURNS_DEFAULTS)
        Mockito.`when`(
            mockRocket.country
        ).thenReturn("country")
        Mockito.`when`(
            mockRocket.costPerLaunch
        ).thenReturn(1)
        Mockito.`when`(
            mockRocket.name
        ).thenReturn("name")
        val mockResponse: MutableList<Rocket> =
            mutableListOf(
                mockRocket
            )
        val mapped = mockResponse.toRockets()
        assertEquals(mapped[0].country, "country")
        assertEquals(mapped[0].cost, "1 Per Launch")
        assertEquals(mapped[0].name, "name")
    }

    @Test
    fun getNext() {
        val mockData =
            Mockito.mock(GetNextLaunchQuery.Data::class.java)
        val mockLaunch = getNextLaunches()
        Mockito.`when`(mockData.launchNext).thenReturn(
            mockLaunch
        )
        val mockResponse =
            ApolloResponse.Builder(
                operation = GetNextLaunchQuery(),
                requestUuid = UUID.randomUUID(),
                data = mockData
            ).build()
        val mapped = mockResponse.toNextLaunch()
        assertEquals(mapped.launchData.mission.name, "My mission name")
        assertEquals(mapped.launchData.mission.date, Date(1111))
        assertEquals(mapped.launchData.number, "id")
    }

    private fun getNextLaunches(): GetNextLaunchQuery.LaunchNext {
        val mockLaunch =
            Mockito.mock(GetNextLaunchQuery.LaunchNext::class.java, Answers.RETURNS_DEEP_STUBS)
        mockLaunch.apply {
            Mockito.`when`(mockLaunch.mission_name).thenReturn("My mission name")
            Mockito.`when`(mockLaunch.launch_date_local).thenReturn(Date(1111))
            Mockito.`when`(
                mockLaunch.id
            ).thenReturn("id")
        }
        return mockLaunch
    }
}

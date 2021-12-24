package com.example.myspacexdemoapp.ui.launch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.example.spacexdemoapp.api.SpaceXApi
import io.reactivex.rxjava3.core.Flowable
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import spacexdemoapp.GetLaunchQuery
import spacexdemoapp.test.GetLaunchQuery_TestBuilder.Data
import java.util.UUID

@RunWith(MockitoJUnitRunner::class)
class LaunchDetailsViewModelTest : TestCase() {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val apolloClient: ApolloClient = mock(ApolloClient::class.java)
    private val spaceXApi = mock(SpaceXApi(apolloClient)::class.java)
    private val viewModel by lazy {
        LaunchDetailsViewModel(spaceXApi)
    }

    @Test
    fun `when get launches initialized then success is retrieved`() {
        val data = GetLaunchQuery.Data {
            launch = launch {
                details = "My details"
                rocket = rocket {
                    rocket_name = "AC"
                }
                mission_name = "My mission name"
            }
        }
        val mockResponse =
            ApolloResponse.Builder(
                operation = GetLaunchQuery("9"),
                requestUuid = UUID.randomUUID(),
                data = data
            ).build()
        `when`(spaceXApi.getLaunchById("9")).thenReturn(
            Flowable.just(
                mockResponse
            )
        )
        val mockObserver = mock(Observer::class.java) as Observer<LaunchDetailsViewState>
        viewModel.launchLiveData.observeForever(mockObserver)
        viewModel.getLaunch("9")
        val argumentCaptor = ArgumentCaptor.forClass(LaunchDetailsViewState::class.java)
        verify(mockObserver, times(2)).onChanged(argumentCaptor.capture())
        assert(argumentCaptor.allValues.first() is LaunchDetailsViewState.Loading)
        assert(argumentCaptor.allValues.last() is LaunchDetailsViewState.Success)
        val actualState = argumentCaptor.allValues.last() as LaunchDetailsViewState.Success
        assertEquals(actualState.model.mission.rocketName, "AC")
        assertEquals(actualState.model.mission.details, "My details")
        assertEquals(actualState.model.mission.name, "My mission name")
    }

    @Test
    fun `when get launches initialized then error is retrieved`() {
        `when`(spaceXApi.getLaunchById("9")).thenReturn(
            Flowable.error(
                Throwable()
            )
        )
        val mockObserver = mock(Observer::class.java) as Observer<LaunchDetailsViewState>
        viewModel.launchLiveData.observeForever(mockObserver)
        viewModel.getLaunch("9")
        val argumentCaptor = ArgumentCaptor.forClass(LaunchDetailsViewState::class.java)
        verify(mockObserver, times(2)).onChanged(argumentCaptor.capture())
        assert(argumentCaptor.allValues.first() is LaunchDetailsViewState.Loading)
        assert(argumentCaptor.allValues.last() is LaunchDetailsViewState.Error)
    }
}

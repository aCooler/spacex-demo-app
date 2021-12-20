package com.example.myspacexdemoapp.ui.launches

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.example.spacexdemoapp.GetLaunchesQuery
import io.reactivex.rxjava3.core.Observable
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Answers
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LaunchesViewModelTest : TestCase() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val apolloClient: ApolloClient = mock(ApolloClient::class.java)
    private val spaceXApi = mock(com.example.spacexdemoapp.api.SpaceXApi(apolloClient)::class.java)
    private val viewModel by lazy {
        LaunchesViewModel(spaceXApi)
    }

    @Test
    fun `when launch by id initialized then success is retrieved`() {
        val mockResponse: Response<GetLaunchesQuery.Data> =
            mock(Response::class.java) as Response<GetLaunchesQuery.Data>
        val mockData =
            mock(GetLaunchesQuery.Data::class.java)
        val mockLaunches = getLaunches()
        `when`(mockData.launches()).thenReturn(
            mockLaunches
        )
        `when`(mockResponse.data).thenReturn(mockData)
        `when`(spaceXApi.getLaunches()).thenReturn(
            Observable.just(
                mockResponse
            )
        )
        val mockObserver = mock(Observer::class.java) as Observer<LaunchesViewState>
        viewModel.launchesLiveData.observeForever(mockObserver)
        viewModel.getLaunches()
        val argumentCaptor = ArgumentCaptor.forClass(LaunchesViewState::class.java)
        verify(mockObserver, times(2)).onChanged(argumentCaptor.capture())
        assert(argumentCaptor.allValues.first() is LaunchesViewState.Loading)
        assert(argumentCaptor.allValues.last() is LaunchesViewState.Success)
        val actualState = argumentCaptor.allValues.last() as LaunchesViewState.Success
        assertEquals(actualState.model?.get(0)?.number, "1111")
        assertEquals(actualState.model?.get(0)?.mission?.details, "My details")
        assertEquals(actualState.model?.get(0)?.mission?.name, "My mission name")
    }

    @Test
    fun `when launch by id initialized then error is retrieved`() {
        `when`(spaceXApi.getLaunches()).thenReturn(
            Observable.error(
                Throwable()
            )
        )
        val mockObserver = mock(Observer::class.java) as Observer<LaunchesViewState>
        viewModel.launchesLiveData.observeForever(mockObserver)
        viewModel.getLaunches()
        val argumentCaptor = ArgumentCaptor.forClass(LaunchesViewState::class.java)
        verify(mockObserver, times(2)).onChanged(argumentCaptor.capture())
        assert(argumentCaptor.allValues.first() is LaunchesViewState.Loading)
        assert(argumentCaptor.allValues.last() is LaunchesViewState.Error)
    }

    private fun getLaunches(): List<GetLaunchesQuery.Launch> {
        val mockLaunch =
            mock(GetLaunchesQuery.Launch::class.java, Answers.RETURNS_DEEP_STUBS)
        mockLaunch.apply {
            `when`(mockLaunch.id()).thenReturn("1111")
            `when`(mockLaunch.details()).thenReturn("My details")
            `when`(
                mockLaunch.fragments().missionDetails().mission_name()
            ).thenReturn("My mission name")
        }
        return listOf(mockLaunch)
    }
}

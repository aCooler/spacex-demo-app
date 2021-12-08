package com.example.myspacexdemoapp.ui.launch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.example.myspacexdemoapp.api.SpaceXApi
import com.example.spacexdemoapp.GetLaunchQuery
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
class LaunchDetailsViewModelTest : TestCase() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val apolloClient: ApolloClient = mock(ApolloClient::class.java)
    private val spaceXApi = mock(SpaceXApi(apolloClient)::class.java)
    private val viewModel by lazy {
        LaunchDetailsViewModel(spaceXApi)
    }

    @Test
    fun `when launch initialized then success is retrieved`() {
        val mockResponse: Response<GetLaunchQuery.Data> =
            mock(Response::class.java) as Response<GetLaunchQuery.Data>
        val mockData =
            mock(GetLaunchQuery.Data::class.java)
        val mockLaunch = getLaunch()
        `when`(mockData.launch()).thenReturn(
            mockLaunch
        )
        `when`(mockResponse.data).thenReturn(mockData)
        `when`(spaceXApi.getLaunchById("9")).thenReturn(
            Observable.just(
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
        assertEquals(actualState.model?.mission?.rocketName, "AC")
        assertEquals(actualState.model?.mission?.details, "My details")
        assertEquals(actualState.model?.mission?.name, "My mission name")
    }

    @Test
    fun `when launch initialized then error is retrieved`() {
        `when`(spaceXApi.getLaunchById("9")).thenReturn(
            Observable.error(
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

    private fun getLaunch(): GetLaunchQuery.Launch {
        val mockLaunch =
            mock(GetLaunchQuery.Launch::class.java, Answers.RETURNS_DEEP_STUBS)
        mockLaunch.apply {
            `when`(mockLaunch.rocket()?.fragments()?.rocketFields()?.rocket_name()).thenReturn("AC")
            `when`(mockLaunch.details()).thenReturn("My details")
            `when`(
                mockLaunch.fragments().missionDetails().mission_name()
            ).thenReturn("My mission name")
        }
        return mockLaunch
    }
}

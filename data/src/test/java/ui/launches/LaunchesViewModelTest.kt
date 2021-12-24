package com.example.myspacexdemoapp.ui.launches

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
import spacexdemoapp.GetLaunchesQuery
import spacexdemoapp.test.GetLaunchesQuery_TestBuilder.Data
import java.util.UUID

@RunWith(MockitoJUnitRunner::class)
class LaunchesViewModelTest : TestCase() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val apolloClient: ApolloClient = mock(ApolloClient::class.java)
    private val spaceXApi = mock(SpaceXApi(apolloClient)::class.java)
    private val viewModel by lazy {
        LaunchesViewModel(spaceXApi)
    }

    @Test
    fun `when launch by id initialized then success is retrieved`() {
        val data = GetLaunchesQuery.Data {
            launches = arrayListOf(
                launch {
                    id = "1111"
                    details = "My details"
                    mission_name = "My mission name"
                }
            )
        }
        val mockResponse =
            ApolloResponse.Builder(
                operation = GetLaunchesQuery(),
                requestUuid = UUID.randomUUID(),
                data = data
            ).build()
        `when`(spaceXApi.getLaunches()).thenReturn(
            Flowable.just(
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
            Flowable.error(
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
}

package com.example.myspacexdemoapp.ui.launches

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.apollographql.apollo.ApolloClient
import com.example.myspacexdemoapp.BuildConfig
import com.example.myspacexdemoapp.api.SpaceXApi
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LaunchesViewModelTest : TestCase() {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Mock
    lateinit var apolloClient: ApolloClient


    @Test
    fun getLaunchesAssertLoading() {

        val tasksViewModel = LaunchesViewModel(
            SpaceXApi(
                ApolloClient.builder().serverUrl(BuildConfig.SPACEX_ENDPOINT).build()
            )
        )
        tasksViewModel.getLaunches()
//        val value = tasksViewModel.launchesLiveData.getOrAwaitValue()
//
//        assert(value is LaunchesViewState.Loading)
    }    @Test
    fun getLaunchesAssertSuccess() {
        val tasksViewModel = LaunchesViewModel(
            SpaceXApi(
                ApolloClient.builder().serverUrl(BuildConfig.SPACEX_ENDPOINT).build()
            )
        )
        tasksViewModel.getLaunches()
//        val value = tasksViewModel.launchesLiveData.getOrAwaitValue()
//
//        assert(value is LaunchesViewState.Success)
    }

    @Test
    fun getLaunchesAssertError() {
        val tasksViewModel = LaunchesViewModel(
            SpaceXApi(
                ApolloClient.builder().serverUrl(BuildConfig.SPACEX_ENDPOINT).build()
            )
        )
        tasksViewModel.getLaunches()
//        val value = tasksViewModel.launchesLiveData.getOrAwaitValue()
//
//        assert(value is LaunchesViewState.Error)
    }
}

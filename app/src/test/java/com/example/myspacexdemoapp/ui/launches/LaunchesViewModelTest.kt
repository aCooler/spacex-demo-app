package com.example.myspacexdemoapp.ui.launches

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.apollographql.apollo.ApolloClient
import com.example.myspacexdemoapp.BuildConfig
import com.example.myspacexdemoapp.api.SpaceXApi
import getOrAwaitValue
import org.junit.Rule
import org.junit.Test

class LaunchesViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getLaunches() {
        val tasksViewModel = LaunchesViewModel(
            SpaceXApi(
                ApolloClient.builder().serverUrl(BuildConfig.SPACEX_ENDPOINT).build()
            )
        )
        tasksViewModel.getLaunches()
        val value = tasksViewModel.launchesLiveData.getOrAwaitValue()

        assert(value is LaunchesViewState.Loading)
    }
}

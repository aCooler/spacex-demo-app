package com.example.myspacexdemoapp.ui.launch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.apollographql.apollo.ApolloClient
import com.example.myspacexdemoapp.BuildConfig
import com.example.myspacexdemoapp.api.SpaceXApi
import getOrAwaitValue
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test

class LaunchDetailsViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getLaunch() {
        val tasksViewModel = LaunchDetailsViewModel(
            SpaceXApi(
                ApolloClient.builder().serverUrl(BuildConfig.SPACEX_ENDPOINT).build()
            )
        )
        tasksViewModel.getLaunch("9")
        val value = tasksViewModel.launchLiveData.getOrAwaitValue()
        assertNotNull(value)
    }
}


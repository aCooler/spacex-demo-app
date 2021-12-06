package com.example.myspacexdemoapp.api

import com.apollographql.apollo.ApolloClient
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SpaceXApiTest : TestCase() {
    private var apolloClient: ApolloClient = mock(ApolloClient::class.java)

    @Mock
    var spaceXApi: SpaceXApi = SpaceXApi(apolloClient)

    @Test
    fun getLaunches() {
        spaceXApi.getLaunches()
        verify(spaceXApi).getLaunches()
    }

    @Test
    fun getLaunchById() {
        spaceXApi.getLaunchById("9")
        verify(spaceXApi).getLaunchById("9")
    }
}

package com.example.myspacexdemoapp.api

import com.apollographql.apollo3.ApolloClient
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import spacexdemoapp.GetLaunchQuery
import spacexdemoapp.GetLaunchesQuery

@RunWith(MockitoJUnitRunner::class)
class SpaceXApiTest : TestCase() {
    private val apolloClient: ApolloClient = mock(ApolloClient::class.java)
    private fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()

    @Test
    fun getLaunches() {
        val queryCaptor: ArgumentCaptor<GetLaunchesQuery> =
            ArgumentCaptor.forClass(GetLaunchesQuery::class.java)
        val query = GetLaunchesQuery()
        apolloClient.query(query)
        verify(apolloClient).query(capture(queryCaptor))
        assertEquals(query, queryCaptor.value)
    }

    @Test
    fun getLaunchById() {
        val queryCaptor: ArgumentCaptor<GetLaunchQuery> =
            ArgumentCaptor.forClass(GetLaunchQuery::class.java)
        val query = GetLaunchQuery("9")
        apolloClient.query(query)
        verify(apolloClient).query(capture(queryCaptor))
        assertEquals(query, queryCaptor.value)
    }
}

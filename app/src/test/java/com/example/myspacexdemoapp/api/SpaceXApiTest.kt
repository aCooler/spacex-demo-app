package com.example.myspacexdemoapp.api

import com.apollographql.apollo.ApolloClient
import com.example.spacexdemoapp.GetLaunchQuery
import com.example.spacexdemoapp.GetLaunchesQuery
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SpaceXApiTest : TestCase() {
    private val apolloClient: ApolloClient = mock(ApolloClient::class.java)

    @Test
    fun getLaunches() {
        val queryCaptor: ArgumentCaptor<GetLaunchesQuery> =
            ArgumentCaptor.forClass(GetLaunchesQuery::class.java)
        val query = GetLaunchesQuery()
        apolloClient.query(query)
        verify(apolloClient).query(queryCaptor.capture())
        assertEquals(query, queryCaptor.value)
    }

    @Test
    fun getLaunchById() {
        val queryCaptor: ArgumentCaptor<GetLaunchQuery> =
            ArgumentCaptor.forClass(GetLaunchQuery::class.java)
        val query = GetLaunchQuery("9")
        apolloClient.query(query)
        verify(apolloClient).query(queryCaptor.capture())
        assertEquals(query, queryCaptor.value)
    }
}

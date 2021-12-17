package com.example.myspacexdemoapp.di

import com.apollographql.apollo.ApolloClient
import com.example.myspacexdemoapp.BuildConfig
import com.example.myspacexdemoapp.api.SpaceXApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class NetModule {

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        val client = ApolloClient.builder().serverUrl(BuildConfig.SPACEX_ENDPOINT)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideSpaceXApi(apolloClient: ApolloClient): SpaceXApi {
        return SpaceXApi(apolloClient)
    }
}
package com.example.myspacexdemoapp.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.adapter.DateAdapter
import com.example.domain.LaunchRepository
import com.example.domain.LaunchRepositoryImpl
import com.example.myspacexdemoapp.BuildConfig
import com.example.spacexdemoapp.api.SpaceXApi
import dagger.Module
import dagger.Provides
import spacexdemoapp.type.Date
import javax.inject.Singleton

@Module
class NetModule {

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        val client = ApolloClient.Builder().serverUrl(BuildConfig.SPACEX_ENDPOINT)
            .addCustomScalarAdapter(Date.type, DateAdapter)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideSpaceXApi(apolloClient: ApolloClient): SpaceXApi {
        return SpaceXApi(apolloClient)
    }

    @Provides
    @Singleton
    fun provideLaunchesRepository(spaceXApi: SpaceXApi): LaunchRepository {
        return LaunchRepositoryImpl(spaceXApi)
    }
}

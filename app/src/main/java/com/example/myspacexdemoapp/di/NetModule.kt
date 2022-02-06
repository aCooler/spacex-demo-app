package com.example.myspacexdemoapp.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.adapter.DateAdapter
import com.example.domain.LaunchRepository
import com.example.myspacexdemoapp.BuildConfig
import com.example.spacexdemoapp.api.DataLaunchRepository
import com.example.spacexdemoapp.api.SpaceXApi
import com.example.spacexdemoapp.api.retrofit.RocketsService
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideRetrofitClient(): Retrofit {
        var retrofit: Retrofit? = null
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.spacexdata.com/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
        }
        return retrofit!!
    }

    @Provides
    @Singleton
    fun provideRocketsService(retrofit: Retrofit): RocketsService {
        return retrofit.create(RocketsService::class.java)
    }

    @Provides
    @Singleton
    fun provideSpaceXApi(apolloClient: ApolloClient): SpaceXApi {
        return SpaceXApi(apolloClient)
    }

    @Provides
    @Singleton
    fun provideLaunchesRepository(spaceXApi: SpaceXApi, rocketsService: RocketsService): LaunchRepository {
        return DataLaunchRepository(spaceXApi, rocketsService)
    }
}

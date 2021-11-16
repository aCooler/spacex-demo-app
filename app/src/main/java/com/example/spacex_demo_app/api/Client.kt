package com.example.spacex_demo_app.api

import android.os.Handler
import android.os.Message
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.spacex_demo_app.GetLaunchQuery
import com.example.spacex_demo_app.GetLaunchesQuery

class Client(var handler: Handler?) {

    fun initialise() {
        Thread {
            val apolloClient =
                ApolloClient.builder().serverUrl("https://api.spacex.land/graphql/").build()
            val query = apolloClient.query(GetLaunchesQuery())


            query?.enqueue(object : ApolloCall.Callback<GetLaunchesQuery.Data>() {

                override fun onResponse(response: Response<GetLaunchesQuery.Data>) {
                    if (!response.hasErrors()) {
                        val message = Message()
                        message.data.putString("launch", response.data?.launches().toString())
                        handler?.sendMessage(message)
                    }
                }

                override fun onFailure(e: ApolloException) {

                }
            })
        }.start()

    }


}




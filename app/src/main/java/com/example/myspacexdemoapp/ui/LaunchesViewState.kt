package com.example.myspacexdemoapp.ui

import com.apollographql.apollo.api.Response
import com.example.spacexdemoapp.GetLaunchesQuery


sealed class LaunchesViewState() {

    class Success(val model: Response<GetLaunchesQuery.Data>): LaunchesViewState(){}

    class Error(val error: Throwable): LaunchesViewState()


}





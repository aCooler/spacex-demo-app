package com.example.myspacexdemoapp.ui.launch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.apollographql.apollo.ApolloClient
import com.example.myspacexdemoapp.BuildConfig
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.api.SpaceXApi
import com.example.myspacexdemoapp.ui.launches.LaunchesViewModel
import com.example.myspacexdemoapp.ui.launches.LaunchesViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var viewModel: LaunchViewModel
    private lateinit var viewModelFactory: LaunchesViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val apolloClient =
            ApolloClient.builder().serverUrl(BuildConfig.SPACEX_ENDPOINT).build()
        viewModelFactory = LaunchesViewModelFactory(SpaceXApi(apolloClient))
        viewModel = ViewModelProvider(this, viewModelFactory).get(LaunchViewModel::class.java)

        viewModel.getLaunch("9")
    }
}
package com.example.myspacexdemoapp.ui.launch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.apollographql.apollo.ApolloClient
import com.example.myspacexdemoapp.BuildConfig
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.api.SpaceXApi
import com.example.myspacexdemoapp.ui.launches.LaunchesViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var viewModel: LaunchDetailsViewModel
    private lateinit var viewModelFactory: LaunchesViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val apolloClient =
            ApolloClient.builder().serverUrl(BuildConfig.SPACEX_ENDPOINT).build()
        viewModelFactory = LaunchesViewModelFactory(SpaceXApi(apolloClient))
        viewModel = ViewModelProvider(this, viewModelFactory).get(LaunchDetailsViewModel::class.java)

        viewModel.getLaunch("9")

        viewModel.launchLiveData.observe(this, { state ->
            when (state) {
                is LaunchDetailsViewState.Error -> {
                    // TODO
                }
                is LaunchDetailsViewState.Success -> {
                    title = state.model?.mission?.name ?: getString(R.string.title)
                }
                is LaunchDetailsViewState.Loading -> {
                    // TODO
                }
            }
        })
    }
}

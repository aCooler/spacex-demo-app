package com.example.myspacexdemoapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.apollographql.apollo.ApolloClient
import com.example.myspacexdemoapp.BuildConfig
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.api.SpaceXApi
import com.jakewharton.rxbinding4.view.clicks

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: LaunchesViewModel
    private lateinit var viewModelFactory: LaunchesViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apolloClient =
            ApolloClient.builder().serverUrl(BuildConfig.SPACEX_ENDPOINT).build()
        viewModelFactory = LaunchesViewModelFactory(SpaceXApi(apolloClient))
        viewModel = ViewModelProvider(this, viewModelFactory).get(LaunchesViewModel::class.java)


        val textView: TextView = findViewById(R.id.launches)
        val button: Button = findViewById(R.id.button)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        button.clicks().subscribe {
            viewModel.getLaunches()
        }

        viewModel.launchesLiveData.observe(this, { state ->
            when (state) {
                is LaunchesViewState.Error -> {state.error.localizedMessage
                    textView.text = state.error.message
                    button.visibility = View.VISIBLE
                    progressBar.visibility = View.INVISIBLE
                }
                is LaunchesViewState.Success -> {
                    textView.text = state.model?.data?.launches()?.get(0)?.details()
                    progressBar.visibility = View.INVISIBLE
                    textView.visibility = View.VISIBLE

                }
                is LaunchesViewState.Loading -> {
                    button.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
            }
        })


    }
}

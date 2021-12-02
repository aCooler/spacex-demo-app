package com.example.myspacexdemoapp.ui.launch

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.apollographql.apollo.ApolloClient
import com.example.myspacexdemoapp.BuildConfig
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.api.SpaceXApi
import com.example.myspacexdemoapp.ui.Mapper
import com.example.myspacexdemoapp.ui.launches.LaunchesViewModelFactory

class DetailsFragment(private val launchId: String) : Fragment(R.layout.details_fragment) {

    private lateinit var viewModel: LaunchDetailsViewModel
    private lateinit var viewModelFactory: LaunchesViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val apolloClient =
            ApolloClient.builder().serverUrl(BuildConfig.SPACEX_ENDPOINT).build()
        viewModelFactory = LaunchesViewModelFactory(SpaceXApi(apolloClient))
        viewModel =
            ViewModelProvider(
                requireActivity(),
                viewModelFactory
            ).get(LaunchDetailsViewModel::class.java)
        val recyclerView: RecyclerView? = getView()?.findViewById(R.id.launches_list)
        val adapter = DetailsRecyclerViewAdapter()
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        val mySwipeRefreshLayout: SwipeRefreshLayout? = getView()?.findViewById(R.id.swipe_refresh)
        viewModel.getLaunch(launchId)
        viewModel.launchLiveData.observe(this, { state ->
            when (state) {
                is LaunchDetailsViewState.Error -> {
                    Log.d("LaunchDetailsViewState", state.error.message ?: "empty message")
                }
                is LaunchDetailsViewState.Success -> {
                    val dataset = Mapper(launchUiModel = state.model!!).launchUiModelToDataModel()
                    adapter.setItems(dataset)
                    activity?.title = state.model.mission?.name
                    mySwipeRefreshLayout?.isRefreshing = false
                }
                is LaunchDetailsViewState.Loading -> {
                    mySwipeRefreshLayout?.isRefreshing = true
                }
            }
        })
        mySwipeRefreshLayout?.setOnRefreshListener {
            viewModel.getLaunch(launchId)
        }
    }

}
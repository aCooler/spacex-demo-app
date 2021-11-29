package com.example.myspacexdemoapp.ui.launches

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.apollographql.apollo.ApolloClient
import com.example.myspacexdemoapp.BuildConfig
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.api.SpaceXApi
import com.example.myspacexdemoapp.ui.ActivitiesManagerViewModel
import com.example.myspacexdemoapp.ui.ActivitiesManagerViewModelFactory
import com.example.myspacexdemoapp.ui.launch.DetailsFragment

class MainFragment() : Fragment(R.layout.main_fragment) {

    private lateinit var activitiesViewModel: ActivitiesManagerViewModel
    private lateinit var activitiesManagerViewModelFactory: ActivitiesManagerViewModelFactory

    private lateinit var launchesViewModel: LaunchesViewModel
    private lateinit var viewModelFactory: LaunchesViewModelFactory


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        activitiesManagerViewModelFactory = ActivitiesManagerViewModelFactory()
        activitiesViewModel = ViewModelProvider(
            requireActivity(),
            activitiesManagerViewModelFactory
        ).get(ActivitiesManagerViewModel::class.java)

        val apolloClient =
            ApolloClient.builder().serverUrl(BuildConfig.SPACEX_ENDPOINT).build()
        viewModelFactory = LaunchesViewModelFactory(SpaceXApi(apolloClient))
        launchesViewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        ).get(LaunchesViewModel::class.java)

        val recyclerView: RecyclerView? = getView()?.findViewById(R.id.launches_list)
        val adapter =
            RecyclerViewAdapter(RecyclerViewAdapter.OnClickListener { activitiesViewModel.startDetailsFragment(it) })
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(activity)

        val mySwipeRefreshLayout: SwipeRefreshLayout? = getView()?.findViewById(R.id.swiperefresh)
        mySwipeRefreshLayout?.setOnRefreshListener {
            launchesViewModel.getLaunches()
        }

        launchesViewModel.getLaunches()

        launchesViewModel.launchesLiveData.observe(this, { state ->
            when (state) {
                is LaunchesViewState.Error -> {
                    //TODO
                }
                is LaunchesViewState.Success -> {
                    adapter.setItems(state.model ?: listOf())
                    mySwipeRefreshLayout?.isRefreshing = false

                }
                is LaunchesViewState.Loading -> {
                    mySwipeRefreshLayout?.isRefreshing = true

                }
            }
        })
    }


}

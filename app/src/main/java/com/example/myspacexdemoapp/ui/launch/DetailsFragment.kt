package com.example.myspacexdemoapp.ui.launch

import android.os.Bundle
import android.view.MenuItem
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
import com.example.myspacexdemoapp.ui.ActivitiesManagerViewModel
import com.example.myspacexdemoapp.ui.ActivitiesManagerViewModelFactory
import com.example.myspacexdemoapp.ui.DataAdapter
import com.example.myspacexdemoapp.ui.launches.LaunchesViewModelFactory

class DetailsFragment(private val launchId: String) : Fragment(R.layout.main_fragment) {

    private lateinit var activitiesViewModel: ActivitiesManagerViewModel
    private lateinit var activitiesManagerViewModelFactory: ActivitiesManagerViewModelFactory

    private lateinit var viewModel: LaunchDetailsViewModel
    private lateinit var viewModelFactory: LaunchesViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        activitiesManagerViewModelFactory = ActivitiesManagerViewModelFactory()
        activitiesViewModel = ViewModelProvider(
            requireActivity(),
            activitiesManagerViewModelFactory
        ).get(ActivitiesManagerViewModel::class.java)

        val apolloClient =
            ApolloClient.builder().serverUrl(BuildConfig.SPACEX_ENDPOINT).build()
        viewModelFactory = LaunchesViewModelFactory(SpaceXApi(apolloClient))
        viewModel =
            ViewModelProvider(
                requireActivity(),
                viewModelFactory
            ).get(LaunchDetailsViewModel::class.java)
        activity?.title = ""
        val recyclerView: RecyclerView? = getView()?.findViewById(R.id.launches_list)
        val adapter = DataAdapter()
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        val mySwipeRefreshLayout: SwipeRefreshLayout? = getView()?.findViewById(R.id.swiperefresh)
        viewModel.getLaunch(launchId)
        viewModel.launchLiveData.observe(this, { state ->
            when (state) {
                is LaunchDetailsViewState.Error -> {
                    //TODO
                }
                is LaunchDetailsViewState.Success -> {
                    adapter.setItems(state.model)
                    activity?.title = state.model.name
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

        //requireActivity().actionBar.home

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        activitiesViewModel.startMainFragment()
        return true
    }
}
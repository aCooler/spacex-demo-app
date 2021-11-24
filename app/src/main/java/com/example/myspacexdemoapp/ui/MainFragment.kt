package com.example.myspacexdemoapp.ui

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

class MainFragment : Fragment(R.layout.main_fragment) {

    private lateinit var viewModel: LaunchesViewModel
    private lateinit var viewModelFactory: LaunchesViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

//                activity?.supportFragmentManager?.commit {
//                    setReorderingAllowed(true)
//                    addToBackStack("main_fragment")
//                    replace<MainFragment>(R.id.fragment_container)
//                }



            }
        })*/

        val recyclerView: RecyclerView? = getView()?.findViewById(R.id.launches_list)
        val adapter = RecyclerViewAdapter()
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(activity)

        val apolloClient =
            ApolloClient.builder().serverUrl(BuildConfig.SPACEX_ENDPOINT).build()
        viewModelFactory = LaunchesViewModelFactory(SpaceXApi(apolloClient))
        viewModel = ViewModelProvider(this, viewModelFactory).get(LaunchesViewModel::class.java)

        val mySwipeRefreshLayout: SwipeRefreshLayout? = getView()?.findViewById(R.id.swiperefresh)
        mySwipeRefreshLayout?.setOnRefreshListener {
            viewModel.getLaunches()

        }

        viewModel.launchesLiveData.observe(this, { state ->
            when (state) {
                is LaunchesViewState.Error -> {
                    //TODO
                }
                is LaunchesViewState.Success -> {
                    adapter.setItems(state.model ?: listOf())
                    mySwipeRefreshLayout?.isRefreshing = false

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
        //viewModel.getLaunches()


    }
}
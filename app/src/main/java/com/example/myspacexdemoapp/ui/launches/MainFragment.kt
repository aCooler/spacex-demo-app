package com.example.myspacexdemoapp.ui.launches

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
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
import com.example.myspacexdemoapp.ui.launch.DetailsFragment

class MainFragment : Fragment(R.layout.main_fragment) {
    private lateinit var launchesViewModel: LaunchesViewModel
    private lateinit var viewModelFactory: LaunchesViewModelFactory
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val apolloClient =
            ApolloClient.builder().serverUrl(BuildConfig.SPACEX_ENDPOINT).build()
        viewModelFactory = LaunchesViewModelFactory(SpaceXApi(apolloClient))
        launchesViewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        ).get(LaunchesViewModel::class.java)
        val recyclerView: RecyclerView? = getView()?.findViewById(R.id.launches_list)
        val adapter =
            RecyclerViewAdapter(RecyclerViewAdapter.OnClickListener { openDetailsFragment(it) })
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(activity)

        val mySwipeRefreshLayout: SwipeRefreshLayout? = getView()?.findViewById(R.id.swipe_refresh)
        mySwipeRefreshLayout?.setOnRefreshListener {
            launchesViewModel.getLaunches()
        }
        launchesViewModel.getLaunches()
        launchesViewModel.launchesLiveData.observe(this, { state ->
            when (state) {
                is LaunchesViewState.Error -> {
                    Log.d("LaunchesViewState.E ", state.error.message ?: "empty message")
                    AlertDialog.Builder(requireActivity())
                        .setMessage(getString(R.string.error))
                        .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                            dialog.cancel()
                        }
                        .show()
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

    private fun openDetailsFragment(id: String?) {
        if (!id.isNullOrEmpty()) {
            requireActivity().supportFragmentManager.commit {
                setReorderingAllowed(true)
                val detailsFragment: Fragment = DetailsFragment(id)
                addToBackStack("details_fragment")
                replace(R.id.fragment_container, detailsFragment)
            }
        }
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)
    }


}


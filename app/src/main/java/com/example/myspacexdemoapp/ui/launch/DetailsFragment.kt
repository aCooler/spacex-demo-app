package com.example.myspacexdemoapp.ui.launch

import android.app.AlertDialog
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
import com.example.myspacexdemoapp.ui.launches.LaunchesViewModelFactory
import com.example.myspacexdemoapp.ui.mappers.LaunchUIMapper


class DetailsFragment(private val launchId: String) : Fragment(R.layout.details_fragment) {

    private lateinit var viewModel: LaunchDetailsViewModel
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var alertDialog: AlertDialog
    lateinit var apolloClient : ApolloClient
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
           apolloClient =  ApolloClient.builder().serverUrl(BuildConfig.SPACEX_ENDPOINT).build()
        viewModelFactory = LaunchesViewModelFactory(SpaceXApi(apolloClient))
        viewModel =
            ViewModelProvider(
                requireActivity(),
                viewModelFactory
            ).get(LaunchDetailsViewModel::class.java)
        val recyclerView: RecyclerView? = getView()?.findViewById(R.id.launches_details_list)
        val adapter = DetailsRecyclerViewAdapter()
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        val mySwipeRefreshLayout: SwipeRefreshLayout? = getView()?.findViewById(R.id.swipe_refresh_details)
        //viewModel.getLaunch(launchId)
        viewModel.launchLiveData.observe(this, { state ->
            when (state) {
                is LaunchDetailsViewState.Error -> {
                    Log.d("LaunchDetailsViewState", state.error.message ?: "empty message")
                    alertDialog = AlertDialog.Builder(requireActivity())
                        .setMessage(state.error.message)
                        .setTitle(getString(R.string.error))
                        .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                            dialog.cancel()
                        }.create()
                        alertDialog.show()
                }
                is LaunchDetailsViewState.Success -> {
                    val dataset =
                        LaunchUIMapper(launchUiModel = state.model).launchUiModelToDataModel()
                    adapter.setItems(dataset)
                    //activity?.title = state.model.mission.name
/*                    var toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar_details)
                    toolbar.title = state.model.mission.name*/
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

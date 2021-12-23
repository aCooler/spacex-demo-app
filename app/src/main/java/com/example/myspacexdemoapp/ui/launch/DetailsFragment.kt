package com.example.myspacexdemoapp.ui.launch

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myspacexdemoapp.MyApp
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.ui.mappers.LaunchUIMapper
import javax.inject.Inject

class DetailsFragment : Fragment(R.layout.details_fragment) {
    @Inject
    lateinit var viewModel: LaunchDetailsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView? = getView()?.findViewById(R.id.launches_details_list)
        val adapter = DetailsRecyclerViewAdapter()
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        val mySwipeRefreshLayout: SwipeRefreshLayout? =
            getView()?.findViewById(R.id.swipe_refresh_details)
        if (DetailsFragmentArgs.fromBundle(arguments ?: bundleOf()).launchId.isNotEmpty()) {
            viewModel.getLaunch(DetailsFragmentArgs.fromBundle(arguments ?: bundleOf()).launchId)
        }
        viewModel.launchLiveData.observe(this, { state ->
            when (state) {
                is LaunchDetailsViewState.Error -> {
                    Log.d("LaunchDetailsViewState", state.error.message ?: "empty message")
                    val alertDialog = AlertDialog.Builder(requireActivity())
                    alertDialog.setMessage(state.error.message)
                        .setTitle(getString(R.string.error))
                        .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                            dialog.cancel()
                        }.create()
                    alertDialog.show()
                }
                is LaunchDetailsViewState.Success -> {
                    val dataset =
                        LaunchUIMapper(launchUiModel = state.model).launchUiModelToDataModel()
                    adapter.setItems(dataset)
                    val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar_details)
                    toolbar.title = state.model.mission.name
                    mySwipeRefreshLayout?.isRefreshing = false
                }
                is LaunchDetailsViewState.Loading -> {
                    mySwipeRefreshLayout?.isRefreshing = true
                }
            }
        })
        mySwipeRefreshLayout?.setOnRefreshListener {
            if (DetailsFragmentArgs.fromBundle(arguments ?: bundleOf()).launchId.isNotEmpty()) {
                viewModel.getLaunch(DetailsFragmentArgs.fromBundle(arguments ?: bundleOf()).launchId)
            }
        }
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as MyApp).appComponent?.inject(this)
        super.onAttach(context)
    }
}

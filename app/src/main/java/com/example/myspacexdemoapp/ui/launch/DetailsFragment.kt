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
import com.example.myspacexdemoapp.databinding.DetailsFragmentBinding
import com.example.myspacexdemoapp.ui.mappers.LaunchUIMapper
import javax.inject.Inject

class DetailsFragment : Fragment(R.layout.details_fragment) {
    @Inject
    lateinit var viewModel: LaunchDetailsViewModel
    private var fragmentBlankBinding: DetailsFragmentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.init(DetailsFragmentArgs.fromBundle(arguments ?: bundleOf()).launchId)

        val binding = DetailsFragmentBinding.bind(view)
        fragmentBlankBinding = bindingval recyclerView: RecyclerView? = getView()?.findViewById(R.id.launches_details_list)
        val adapter = DetailsRecyclerViewAdapter()
        binding.launchesDetailsList.adapter = adapter
        binding.launchesDetailsList.layoutManager = LinearLayoutManager(activity)
        viewModel.getLaunchUI()
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
                    binding.toolbarDetails.title = state.model.mission.name
                    binding.swipeRefreshDetails.isRefreshing = false
                }
                is LaunchDetailsViewState.Loading -> {
                    binding.swipeRefreshDetails.isRefreshing = true
                }
            }
        })
        binding.swipeRefreshDetails.setOnRefreshListener {
            viewModel.getLaunchUI()
        }
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as MyApp).appComponent?.inject(this)
        super.onAttach(context)
    }

    override fun onDestroyView() {
        fragmentBlankBinding = null
        super.onDestroyView()
    }
}

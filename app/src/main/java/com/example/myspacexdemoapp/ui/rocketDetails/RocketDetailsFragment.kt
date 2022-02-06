package com.example.myspacexdemoapp.ui.rocketDetails

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myspacexdemoapp.MyApp
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.databinding.RocketDetailsFragmentBinding
import com.example.myspacexdemoapp.ui.mappers.RocketDetailsUIMapper
import javax.inject.Inject

class RocketDetailsFragment : Fragment(R.layout.rocket_details_fragment) {
    @Inject
    lateinit var viewModel: RocketDetailsViewModel
    private var fragmentBlankBinding: RocketDetailsFragmentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args = RocketDetailsFragmentArgs.fromBundle(arguments ?: bundleOf())
        viewModel.init(args.launchId)
        val binding = RocketDetailsFragmentBinding.bind(view)
        fragmentBlankBinding = binding
        val adapter = RocketDetailsViewAdapter()
        binding.launchesDetailsList.adapter = adapter
        binding.toolbarDetails.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbarDetails.setNavigationOnClickListener { findNavController().navigate(
            R.id.action_myDetailsFragment_to_myRocketsFragment,
            null
        ) }
        binding.launchesDetailsList.layoutManager = LinearLayoutManager(activity)
        viewModel.getLaunchUI()
        viewModel.launchLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is RocketDetailsViewState.Error -> {
                    Log.d("LaunchDetailsViewState", state.error.message ?: "empty message")
                    val alertDialog = AlertDialog.Builder(requireActivity())
                    alertDialog.setMessage(state.error.message)
                        .setTitle(getString(R.string.error))
                        .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                            dialog.cancel()
                        }.create()
                    alertDialog.show()
                }
                is RocketDetailsViewState.Success -> {
                    val dataset =
                        RocketDetailsUIMapper(state.model).rocketDetailsUiModelToDataModel()
                    adapter.setItems(dataset)
                    binding.toolbarDetails.title = state.model.number
                    binding.rocketDetailsSwipeRefreshDetails.isRefreshing = false
                }
                is RocketDetailsViewState.Loading -> {
                    binding.rocketDetailsSwipeRefreshDetails.isRefreshing = true
                }
            }
        }
        binding.rocketDetailsSwipeRefreshDetails.setOnRefreshListener {
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

package com.example.myspacexdemoapp.ui.rockets

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myspacexdemoapp.MyApp
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.databinding.RocketsFragmentBinding
import com.example.myspacexdemoapp.ui.rocketDetails.RocketDetailsFragmentArgs
import javax.inject.Inject

class RocketsFragment : Fragment(R.layout.rockets_fragment) {

    @Inject
    lateinit var rocketsViewModel: RocketsViewModel
    private var fragmentBlankBinding: RocketsFragmentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rocketsViewModel.init(arguments)
        val binding = RocketsFragmentBinding.bind(view)
        fragmentBlankBinding = binding
        val adapter =
            RocketsAdapter(
                RocketsAdapter.OnClickListener { id ->
                    openDetailsFragment(id)
                }
            )
        binding.rocketsList.adapter = adapter
        binding.rocketsList.layoutManager = LinearLayoutManager(activity)
        binding.swipeRefreshRockets.setOnRefreshListener {
            rocketsViewModel.getRockets()
        }
        rocketsViewModel.getRockets()
        rocketsViewModel.rocketsLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is RocketsViewState.Error -> {
                    Log.d("RocketsViewState.E ", state.error.message ?: "empty message")
                    AlertDialog.Builder(requireActivity())
                        .setMessage(state.error.message)
                        .setTitle(getString(R.string.error))
                        .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                            dialog.cancel()
                        }
                        .show()
                }
                is RocketsViewState.Success -> {
                    adapter.setItems(state.model ?: listOf())
                    binding.swipeRefreshRockets.isRefreshing = false
                }
                is RocketsViewState.Loading -> {
                    binding.swipeRefreshRockets.isRefreshing = true
                }
            }
        }
    }

    private fun openDetailsFragment(id: String?) {
        if (!id.isNullOrEmpty()) {
            requireActivity().supportFragmentManager.commit {
                findNavController().navigate(
                    R.id.action_myRocketsFragment_to_myDetailsRocketFragment,
                    RocketDetailsFragmentArgs(launchId = id).toBundle(),
                )
            }
        }
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)
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

package com.example.myspacexdemoapp.ui.launches

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
import com.example.myspacexdemoapp.databinding.MainFragmentBinding
import com.example.myspacexdemoapp.ui.launch.DetailsFragmentArgs
import javax.inject.Inject

class MainFragment : Fragment(R.layout.main_fragment) {

    @Inject
    lateinit var launchesViewModel: LaunchesViewModel
    private var fragmentBlankBinding: MainFragmentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = MainFragmentBinding.bind(view)
        fragmentBlankBinding = binding
        val adapter =
            RecyclerViewAdapter(RecyclerViewAdapter.OnClickListener { openDetailsFragment(it) })
        binding.launchesList.adapter = adapter
        binding.launchesList.layoutManager = LinearLayoutManager(activity)
        binding.swipeRefresh.setOnRefreshListener {
            launchesViewModel.getLaunches()
        }
        launchesViewModel.getLaunches()
        launchesViewModel . launchesLiveData . observe (this, { state ->
            when (state) {
                is LaunchesViewState.Error -> {
                    Log.d("LaunchesViewState.E ", state.error.message ?: "empty message")
                    AlertDialog.Builder(requireActivity())
                        .setMessage(state.error.message)
                        .setTitle(getString(R.string.error))
                        .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                            dialog.cancel()
                        }
                        .show()
                }
                is LaunchesViewState.Success -> {
                    adapter.setItems(state.model ?: listOf())
                    binding.swipeRefresh.isRefreshing = false
                }
                is LaunchesViewState.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                }
            }
        })
    }

    private fun openDetailsFragment(id: String?) {
        if (!id.isNullOrEmpty()) {
            requireActivity().supportFragmentManager.commit {
                findNavController().navigate(
                    R.id.action_myHomeFragment_to_myDetailsFragment,
                    DetailsFragmentArgs(launchId = id).toBundle(),
                    null
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

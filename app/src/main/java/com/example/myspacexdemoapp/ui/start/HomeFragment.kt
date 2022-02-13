package com.example.myspacexdemoapp.ui.start

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myspacexdemoapp.MyApp
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.databinding.HomeFragmentBinding
import com.example.myspacexdemoapp.ui.mappers.toTimerUIList
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

class HomeFragment : Fragment(R.layout.home_fragment) {

    @Inject
    lateinit var timerViewModel: StartViewModel
    private var homeFragmentBinding: HomeFragmentBinding? = null
    var adapter: HomeAdapter? = null
    private var disposable: Disposable? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        timerViewModel.init(arguments)
        val binding = HomeFragmentBinding.bind(view)
        homeFragmentBinding = binding
        val adapter = HomeAdapter(
            HomeAdapter.OnClickListener {
                binding.launchesListTimer.scrollToPosition(2)
            }
        )
        binding.launchesListTimer.adapter = adapter
        binding.launchesListTimer.layoutManager = LinearLayoutManager(activity)
        binding.swipeRefreshStart.setOnRefreshListener {
            timerViewModel.getLaunchNextLaunch()
        }
        timerViewModel.getLaunchNextLaunch()
        timerViewModel.launchLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is StartViewState.Error -> {
                    Log.d("StartViewState.Error", state.error.toString())
                }
                is StartViewState.Success -> {
                    val dataset = state.model.toTimerUIList()
                    adapter.setItems(dataset)
                    binding.swipeRefreshStart.isRefreshing = false
                }
                is StartViewState.Loading -> {
                    binding.swipeRefreshStart.isRefreshing = true
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as MyApp).appComponent?.inject(this)
        super.onAttach(context)
    }

    override fun onDestroyView() {
        disposable?.dispose()
        homeFragmentBinding = null
        super.onDestroyView()
    }
}

package com.example.myspacexdemoapp.ui.start

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myspacexdemoapp.MyApp
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.databinding.StartFragmentBinding
import com.example.myspacexdemoapp.ui.mappers.toTimerUIList
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

class StartFragment : Fragment(R.layout.start_fragment) {

    @Inject
    lateinit var timerViewModel: StartViewModel
    private var fragmentBlankBinding: StartFragmentBinding? = null
    var adapter: StartAdapter? = null
    private var disposable: Disposable? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        timerViewModel.init(arguments)
        val binding = StartFragmentBinding.bind(view)
        fragmentBlankBinding = binding
        val adapter = StartAdapter(
            StartAdapter.OnClickListener {
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
        fragmentBlankBinding = null
        super.onDestroyView()
    }
}


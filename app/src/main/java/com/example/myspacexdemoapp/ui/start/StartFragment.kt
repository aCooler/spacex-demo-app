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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.abs


class StartFragment : Fragment(R.layout.start_fragment) {

    @Inject
    lateinit var timerViewModel: StartViewModel
    private var fragmentBlankBinding: StartFragmentBinding? = null
    var adapter: StartAdapter? = null
    var disposable: Disposable? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = StartFragmentBinding.bind(view)
        fragmentBlankBinding = binding
        val adapter = StartAdapter(StartAdapter.OnClickListener {
            binding.launchesListTimer.scrollToPosition(2)
        })
        binding.launchesListTimer.adapter = adapter
        binding.launchesListTimer.layoutManager = LinearLayoutManager(activity)
        binding.swipeRefresh.setOnRefreshListener {
            timerViewModel.getLaunchNextLaunch()
        }
        timerViewModel.getLaunchNextLaunch()
        var date : Date
        timerViewModel.launchLiveData.observe(this, { state ->
            when (state) {
                is StartViewState.Error -> {
                    Log.d("StartViewState.Error", state.error.toString())
                }
                is StartViewState.Success -> {
                    val nextLaunch = state.model.launchData
                    val rocketsEfficiency = state.model.rockets
                    date = nextLaunch.mission.date
                    val timeToLaunch = Date(abs(date.time - Date().time))
                    val dataset: MutableList<StartUIModel> = mutableListOf()
                    dataset.add(0, StartUIModel.Timer(
                        name = nextLaunch.mission.name,
                        days = timeToLaunch.day.toString(),
                        hours = timeToLaunch.hours.toString(),
                        minutes = timeToLaunch.minutes.toString(),
                        seconds = timeToLaunch.seconds.toString()
                    ))
                    dataset.add(1, StartUIModel.Launches(
                        successful = rocketsEfficiency.efficiency,
                        total = rocketsEfficiency.total,
                        efficiency = "",
                        toLaunches = ""
                    ))
                    dataset.add(2, StartUIModel.Rockets(
                        tweet = ""
                    ))

                    adapter.setItems(dataset)
                    disposable = Flowable.interval(1, 1, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            timeToLaunch.seconds--
                            dataset[0] = StartUIModel.Timer(
                                name = nextLaunch.mission.name,
                                days = timeToLaunch.day.toString(),
                                hours = timeToLaunch.hours.toString(),
                                minutes = timeToLaunch.minutes.toString(),
                                seconds = timeToLaunch.seconds.toString()
                            )
                            adapter.setItems(dataset)
                        }, {
                            Log.d("tag", it.message.toString())
                        })
                    binding.swipeRefresh.isRefreshing = false
                }
                is StartViewState.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                }
            }
        })
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

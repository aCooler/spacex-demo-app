package com.example.myspacexdemoapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myspacexdemoapp.api.SpaceXApi

class LaunchesViewModel(private val spaceXApi: SpaceXApi) : ViewModel() {

    private val _launchesMutableLiveData = MutableLiveData<LaunchesViewState>()
    val launchesLiveData: LiveData<LaunchesViewState> = _launchesMutableLiveData


    fun getLaunches() {
        spaceXApi.getLaunches()
            .subscribe({response ->
                _launchesMutableLiveData.postValue(LaunchesViewState.Success(
                    response.data?.launches()?.map { it ->
                        LaunchUiModel(
                            it.id() ?: "",
                            it.links()?.mission_patch()?:"",
                            it.fragments().missionDetails().mission_name() ?: "",
                            it.fragments().missionDetails().launch_date_utc().toString()?:"",
                            it.rocket()?.__typename() ?: "",
                            it.launch_site()?.site_name_long()?: "",
                            it.fragments().missionDetails().launch_success() ?: true,
                            it.links()?.flickr_images().let { toString() }  ?: ""
                        )
                    }
                ))
            }, { throwable ->
                _launchesMutableLiveData.postValue(LaunchesViewState.Error(throwable))
            })
    }


}
package com.example.myspacexdemoapp.ui.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myspacexdemoapp.api.SpaceXApi

class LaunchesViewModel(private val spaceXApi: SpaceXApi) : ViewModel() {

    private val _launchesMutableLiveData = MutableLiveData<LaunchesViewState>()
    val launchesLiveData: LiveData<LaunchesViewState> = _launchesMutableLiveData

    fun getLaunches() {
        spaceXApi.getLaunches()
            .doOnSubscribe {
                _launchesMutableLiveData.postValue(LaunchesViewState.Loading)
            }
            .subscribe({ response ->
                _launchesMutableLiveData.postValue(LaunchesViewState.Success(
                    response.data?.launches()?.map {
                        LaunchUiModel(
                            it.id() ?: "",
                            it.links()?.mission_patch() ?: "",
                            it.fragments().missionDetails().mission_name() ?: "",
                            it.fragments().missionDetails().launch_date_utc().toString() ?: "",
                            it.rocket()?.fragments()?.rocketFields()?.rocket_name() ?: "",
                            it.launch_site()?.site_name_long() ?: "",
                            it.fragments().missionDetails().launch_success() ?: true,
                            it.links()?.flickr_images().let { pictures ->
                                if (pictures!!.isNotEmpty()) {
                                    pictures[0]
                                } else {
                                    ""
                                }
                            }
                        )
                    }
                ))

            }, { throwable ->
                _launchesMutableLiveData.postValue(LaunchesViewState.Error(throwable))
            })
    }


}
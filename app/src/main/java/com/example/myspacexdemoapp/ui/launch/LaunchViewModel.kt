package com.example.myspacexdemoapp.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myspacexdemoapp.api.SpaceXApi
import com.example.myspacexdemoapp.ui.launches.LaunchUiModel

class LaunchViewModel(private val spaceXApi: SpaceXApi) : ViewModel() {


    private val _launchMutableLiveData = MutableLiveData<LaunchViewState>()
    val launchLiveData: LiveData<LaunchViewState> = _launchMutableLiveData


    fun getLaunch(id: String) {
        spaceXApi.getLaunchById(id)
            .doOnSubscribe {
                _launchMutableLiveData.postValue(LaunchViewState.Loading)
            }
            .subscribe({ response ->
                _launchMutableLiveData.postValue(
                    LaunchViewState.Success(
                        LaunchUiModel(
                            id,
                            response.data?.launch()?.links()?.fragments()?.linkInfo()
                                ?.mission_patch() ?: "",
                            response.data?.launch()?.fragments()?.missionDetails()?.mission_name()
                                ?: "",
                            response.data?.launch()?.fragments()?.missionDetails()
                                ?.launch_date_utc().toString() ?: "",
                            response.data?.launch()?.rocket()?.fragments()?.rocketFields()
                                ?.rocket_name() ?: "",
                            response.data?.launch()?.launch_site()?.site_name_long() ?: "",
                            response.data?.launch()?.fragments()?.missionDetails()?.launch_success()
                                ?: true,
                            response.data?.launch()?.links()?.fragments()?.linkInfo()
                                ?.flickr_images().let { if (it!!.isNotEmpty()) it[0] else "" },
                        )
                    )
                )

            }, { throwable ->
                _launchMutableLiveData.postValue(LaunchViewState.Error(throwable))
            })
    }
}
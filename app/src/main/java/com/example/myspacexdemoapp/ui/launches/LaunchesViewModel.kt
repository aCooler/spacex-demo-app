package com.example.myspacexdemoapp.ui.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myspacexdemoapp.api.SpaceXApi
import com.example.myspacexdemoapp.ui.mappers.toLinksInfo
import com.example.spacexdemoapp.GetLaunchesQuery

class LaunchesViewModel(private val spaceXApi: SpaceXApi) : ViewModel() {

    private val _launchesMutableLiveData = MutableLiveData<LaunchesViewState>()
    val launchesLiveData: LiveData<LaunchesViewState> = _launchesMutableLiveData

    //
    fun getLaunches() {
        spaceXApi.getLaunches()
            .doOnSubscribe {
                _launchesMutableLiveData.postValue(LaunchesViewState.Loading)
            }
            .subscribe(
                { response ->
                    _launchesMutableLiveData.postValue(
                        LaunchesViewState.Success(
                            response.data?.launches()?.map {
                                val linkInfo = it.links()?.toLinksInfo() ?: LinkInfo.EMPTY
                                LaunchUiModel(
                                    number = it.id(),
                                    mission = getMission(it),
                                    linkInfo = linkInfo,
                                    payload = null,
                                )
                            }
                        )
                    )
                }
            ) { throwable ->
                _launchesMutableLiveData.postValue(LaunchesViewState.Error(throwable))
            }
    }

    private fun getMission(it: GetLaunchesQuery.Launch): Mission {
        return Mission(
            name = it.fragments().missionDetails().mission_name(),
            date = it.fragments().missionDetails().launch_date_utc().toString(),
            rocketName = it.rocket()?.fragments()?.rocketFields()?.rocket_name(),
            place = it.launch_site()?.site_name_long(),
            success = it.fragments().missionDetails().launch_success(),
            details = null,
        )
    }
}

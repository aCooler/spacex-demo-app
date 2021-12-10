package com.example.myspacexdemoapp.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myspacexdemoapp.api.SpaceXApi
import com.example.myspacexdemoapp.ui.launches.LaunchUiModel
import com.example.myspacexdemoapp.ui.launches.LinkInfo
import com.example.myspacexdemoapp.ui.mappers.toLinksInfo
import com.example.myspacexdemoapp.ui.mappers.toMission
import com.example.myspacexdemoapp.ui.mappers.toPayload

class LaunchDetailsViewModel(private val spaceXApi: SpaceXApi) : ViewModel() {
    private val _launchMutableLiveData = MutableLiveData<LaunchDetailsViewState>()
    val launchLiveData: LiveData<LaunchDetailsViewState> = _launchMutableLiveData

    fun getLaunch(id: String) {
        spaceXApi.getLaunchById(id)
            .doOnSubscribe {
                _launchMutableLiveData.postValue(LaunchDetailsViewState.Loading)
            }
            .subscribe({ response ->
                val linkInfo = response.data?.launch()?.links()?.toLinksInfo() ?: LinkInfo.EMPTY
                val payload = toPayload(response)
                val mission = toMission(response)

                _launchMutableLiveData.postValue(
                    LaunchDetailsViewState.Success(
                        LaunchUiModel(
                            number = id,
                            mission = mission,
                            payload = payload,
                            linkInfo = linkInfo
                        ),
                    )
                )
            }, { throwable ->
                _launchMutableLiveData.postValue(LaunchDetailsViewState.Error(throwable))
            })
    }


}

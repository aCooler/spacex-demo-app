package com.example.myspacexdemoapp.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myspacexdemoapp.ui.launches.LaunchUiModel
import com.example.myspacexdemoapp.ui.launches.LinkInfo
import com.example.myspacexdemoapp.ui.launches.Mission
import com.example.myspacexdemoapp.ui.launches.Payload
import com.example.myspacexdemoapp.ui.mappers.toLinksInfo
import com.example.myspacexdemoapp.ui.mappers.toMission
import com.example.myspacexdemoapp.ui.mappers.toPayload

class LaunchDetailsViewModel(private val spaceXApi: com.example.spacexdemoapp.api.SpaceXApi) :
    ViewModel() {
    private val _launchMutableLiveData = MutableLiveData<LaunchDetailsViewState>()
    val launchLiveData: LiveData<LaunchDetailsViewState> = _launchMutableLiveData
    private var id: String? = null

    fun init(launchId: String) {
        if (launchId.isNotEmpty()) {
            id = launchId
        }
    }

    fun getLaunchUI() {
        if (!id.isNullOrEmpty()) {
            getLaunch(id ?: "")
        }
    }

    fun getLaunch(id: String) {
        spaceXApi.getLaunchById(id)
            .doOnSubscribe {
                _launchMutableLiveData.postValue(LaunchDetailsViewState.Loading)
            }
            .subscribe({ response ->
                val linkInfo = response.data?.launch?.links?.toLinksInfo() ?: LinkInfo.EMPTY
                val payload = response.data?.payload?.toPayload() ?: Payload.EMPTY
                val mission = response.data?.launch?.toMission() ?: Mission.EMPTY
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

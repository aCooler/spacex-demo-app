package com.example.myspacexdemoapp.ui.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myspacexdemoapp.api.SpaceXApi
import com.example.myspacexdemoapp.ui.mappers.toLinksInfo
import com.example.myspacexdemoapp.ui.mappers.toMission

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
                    with(_launchesMutableLiveData) {
                        postValue(
                            LaunchesViewState.Success(
                                response.data?.launches()?.map {
                                    LaunchUiModel(
                                        number = it.id() ?: LaunchUiModel.EMPTY.number,
                                        mission = it.toMission(),
                                        linkInfo = it.links()?.toLinksInfo() ?: LinkInfo.EMPTY,
                                    )
                                } ?: emptyList()
                            )
                        )
                    }
                }
            ) { throwable ->
                _launchesMutableLiveData.postValue(LaunchesViewState.Error(throwable))
            }
    }


}

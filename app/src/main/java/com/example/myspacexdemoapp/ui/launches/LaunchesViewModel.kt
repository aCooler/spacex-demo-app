package com.example.myspacexdemoapp.ui.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myspacexdemoapp.ui.mappers.toLinksInfo
import com.example.myspacexdemoapp.ui.mappers.toMission
import com.example.spacexdemoapp.api.SpaceXApi
import io.reactivex.rxjava3.disposables.Disposable

class LaunchesViewModel(private val spaceXApi: SpaceXApi) : ViewModel() {

    private val _launchesMutableLiveData = MutableLiveData<LaunchesViewState>()
    val launchesLiveData: LiveData<LaunchesViewState> = _launchesMutableLiveData
    private var disposable: Disposable? = null

    fun getLaunches() {
        disposable = spaceXApi.getLaunches()
            .doOnSubscribe {
                _launchesMutableLiveData.postValue(LaunchesViewState.Loading)
            }
            .subscribe(
                { response ->
                    with(_launchesMutableLiveData) {
                        postValue(
                            LaunchesViewState.Success(
                                response.data?.launches?.map {
                                    LaunchUiModel(
                                        number = it?.id ?: LaunchUiModel.EMPTY.number,
                                        mission = it?.toMission() ?: Mission.EMPTY,
                                        linkInfo = it?.links?.toLinksInfo() ?: LinkInfo.EMPTY,
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

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}

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
            .doOnSubscribe {
                _launchesMutableLiveData.postValue(LaunchesViewState.Loading)
            }
            .subscribe({
                _launchesMutableLiveData.postValue(LaunchesViewState.Success(it))
            }, { throwable ->
                _launchesMutableLiveData.postValue(LaunchesViewState.Error(throwable))
            })
    }


}
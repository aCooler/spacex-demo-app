package com.example.myspacexdemoapp.ui.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.GetStartUseCase
import javax.inject.Inject

class StartViewModel @Inject constructor(private val getStartUseCase: GetStartUseCase) :
    ViewModel() {

    private val _launchMutableLiveData = MutableLiveData<StartViewState>()
    val launchLiveData: LiveData<StartViewState> = _launchMutableLiveData


    fun getLaunchNextLaunch() {
        getStartUseCase.invoke()
            .doOnSubscribe { _launchMutableLiveData.postValue(StartViewState.Loading) }
            .subscribe({ response ->
                _launchMutableLiveData.postValue(
                    StartViewState.Success(response)
                )
            }, { throwable ->
                _launchMutableLiveData.postValue(StartViewState.Error(throwable))
            })
    }
}
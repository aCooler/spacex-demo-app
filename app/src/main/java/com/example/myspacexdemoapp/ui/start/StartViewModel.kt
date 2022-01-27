package com.example.myspacexdemoapp.ui.start

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.GetStartUseCase
import javax.inject.Inject

class StartViewModel @Inject constructor(private val getStartUseCase: GetStartUseCase) :
    ViewModel() {

    private val _launchMutableLiveData = MutableLiveData<StartViewState>()
    val launchLiveData: LiveData<StartViewState> = _launchMutableLiveData
    var isNotTest: Boolean = false

    fun init(arguments: Bundle?) {
        isNotTest = arguments?.getBoolean("isNotTest") ?: true
    }

    fun getLaunchNextLaunch() {
        check(isNotTest) {
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
}
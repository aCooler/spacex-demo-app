package com.example.myspacexdemoapp.ui.rocketDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.RocketDetailsUseCase
import javax.inject.Inject

class RocketDetailsViewModel @Inject constructor(private val getRocketDetailsUseCase: RocketDetailsUseCase) :
    ViewModel() {
    private val _launchMutableLiveData = MutableLiveData<RocketDetailsViewState>()
    val launchLiveData: LiveData<RocketDetailsViewState> = _launchMutableLiveData
    private var id: String? = null
    private var payId: String? = null

    fun init(launchId: String) {
        if (launchId.isNotEmpty()) {
            id = launchId
        }
    }

    fun getLaunchUI() {
        if (!id.isNullOrEmpty()) {
            getLaunch(id ?: "", payId ?: "")
        }
    }

    fun getLaunch(id: String, payloadId: String) {
        getRocketDetailsUseCase.invoke(id, payloadId)
            .doOnSubscribe {
                _launchMutableLiveData.postValue(RocketDetailsViewState.Loading)
            }
            .subscribe({ response ->
                _launchMutableLiveData.postValue(
                    RocketDetailsViewState.Success(response)
                )
            }, { throwable ->
                _launchMutableLiveData.postValue(RocketDetailsViewState.Error(throwable))
            })
    }
}

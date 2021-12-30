package com.example.myspacexdemoapp.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.GetLaunchDetailsUseCase
import javax.inject.Inject

class LaunchDetailsViewModel @Inject constructor(private val useCase: GetLaunchDetailsUseCase) :
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
        useCase.invoke(id)
            .doOnSubscribe {
                _launchMutableLiveData.postValue(LaunchDetailsViewState.Loading)
            }
            .subscribe({ response ->
                _launchMutableLiveData.postValue(
                    LaunchDetailsViewState.Success(response)
                )
            }, { throwable ->
                _launchMutableLiveData.postValue(LaunchDetailsViewState.Error(throwable))
            })
    }
}

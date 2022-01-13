package com.example.myspacexdemoapp.ui.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.GetLaunchesUseCase
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

class LaunchesViewModel @Inject constructor(private val getLaunchesUseCase: GetLaunchesUseCase) : ViewModel() {

    private val _launchesMutableLiveData = MutableLiveData<LaunchesViewState>()
    val launchesLiveData: LiveData<LaunchesViewState> = _launchesMutableLiveData
    private var disposable: Disposable? = null

    fun getLaunches() {
        disposable = getLaunchesUseCase.invoke()
            .doOnSubscribe { _launchesMutableLiveData.postValue(LaunchesViewState.Loading) }
            .subscribe(
                { response ->
                    with(_launchesMutableLiveData) {
                        postValue(
                            LaunchesViewState.Success(response)
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

package com.example.myspacexdemoapp.ui.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetLaunchesUseCase
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class LaunchesViewModel @Inject constructor(private val useCase: GetLaunchesUseCase) : ViewModel() {

    private val _launchesMutableLiveData = MutableLiveData<LaunchesViewState>()
    val launchesLiveData: LiveData<LaunchesViewState> = _launchesMutableLiveData
    private var disposable: Disposable? = null

    fun getLaunches() {
        viewModelScope.launch {
            useCase.invoke()
                .onStart {
                    _launchesMutableLiveData.postValue(LaunchesViewState.Loading)
                }
                .catch { exception ->
                    _launchesMutableLiveData.postValue(LaunchesViewState.Error(exception))
                }
                .collect { response ->
                    with(_launchesMutableLiveData) {
                        postValue(
                            LaunchesViewState.Success(response)
                        )
                    }
                }

        }
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}

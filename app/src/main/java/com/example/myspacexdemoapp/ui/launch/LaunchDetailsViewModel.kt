package com.example.myspacexdemoapp.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetLaunchDetailsUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
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
        if (!id.isNullOrEmpty()) getLaunch(id ?: "")
    }

    fun getLaunch(id: String) {
        viewModelScope.launch {
            useCase.invoke(id)
                .onStart {
                    _launchMutableLiveData.postValue(LaunchDetailsViewState.Loading)
                }
                .catch { exception ->
                    _launchMutableLiveData.postValue(LaunchDetailsViewState.Error(exception))
                }
                .collect { response ->
                    _launchMutableLiveData.postValue(
                        LaunchDetailsViewState.Success(response)
                    )
                }

        }
    }
}

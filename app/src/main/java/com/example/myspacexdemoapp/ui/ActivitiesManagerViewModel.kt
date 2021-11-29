package com.example.myspacexdemoapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivitiesManagerViewModel: ViewModel() {

    private val _launchesMutableLiveData = MutableLiveData<ActivitiesManagerViewState>()
    val launchesLiveData: LiveData<ActivitiesManagerViewState> = _launchesMutableLiveData

    fun startMainFragment(){
        _launchesMutableLiveData.postValue(ActivitiesManagerViewState.Main)
    }

    fun startDetailsFragment(id:String){
        _launchesMutableLiveData.postValue(ActivitiesManagerViewState.Details(id))

    }

}
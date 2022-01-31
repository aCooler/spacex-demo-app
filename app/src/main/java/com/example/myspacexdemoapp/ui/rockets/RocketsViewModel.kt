package com.example.myspacexdemoapp.ui.rockets

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.GetRocketsUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RocketsViewModel @Inject constructor(private val getRocketsUseCase: GetRocketsUseCase) :
    ViewModel() {

    var isNotTest: Boolean = true

    private val _rocketsMutableLiveData = MutableLiveData<RocketsViewState>()
    val rocketsLiveData: LiveData<RocketsViewState> = _rocketsMutableLiveData
    private var disposable: Disposable? = null

    fun getRockets() {
        if (isNotTest) {
            disposable = getRocketsUseCase.invoke().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _rocketsMutableLiveData.postValue(RocketsViewState.Loading) }
                .subscribe(
                    { response ->
                        with(_rocketsMutableLiveData) {
                            postValue(
                                RocketsViewState.Success(response)
                            )
                        }
                    }
                ) { throwable ->
                    _rocketsMutableLiveData.postValue(RocketsViewState.Error(throwable))
                }
        }
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

    fun init(arguments: Bundle?) {
        isNotTest = arguments?.getBoolean("isNotTest") ?: true
    }
}

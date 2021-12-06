package com.example.myspacexdemoapp.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apollographql.apollo.api.Response
import com.example.myspacexdemoapp.api.SpaceXApi
import com.example.myspacexdemoapp.ui.launches.LaunchUiModel
import com.example.myspacexdemoapp.ui.launches.LinkInfo
import com.example.myspacexdemoapp.ui.launches.Mission
import com.example.myspacexdemoapp.ui.launches.Payload
import com.example.spacexdemoapp.GetLaunchQuery

class LaunchDetailsViewModel(private val spaceXApi: SpaceXApi) : ViewModel() {
    private val _launchMutableLiveData = MutableLiveData<LaunchDetailsViewState>()
    val launchLiveData: LiveData<LaunchDetailsViewState> = _launchMutableLiveData

    fun getLaunch(id: String) {
        spaceXApi.getLaunchById(id)
            .doOnSubscribe {
                _launchMutableLiveData.postValue(LaunchDetailsViewState.Loading)
            }
            .subscribe({ response ->
                _launchMutableLiveData.postValue(
                    LaunchDetailsViewState.Success(
                        LaunchUiModel(
                            number = id,
                            mission = getMission(response),
                            payload = getPayload(response),
                            linkInfo = getLinkInfo(response)
                        ),
                    )
                )
            }, { throwable ->
                _launchMutableLiveData.postValue(LaunchDetailsViewState.Error(throwable))
            })
    }

    private fun getLinkInfo(response: Response<GetLaunchQuery.Data>?): LinkInfo {
        return LinkInfo(
            badge = response?.data?.launch()?.links()?.fragments()?.linkInfo()
                ?.mission_patch(),
            picture = response?.data?.launch()?.links()?.fragments()?.linkInfo()
                ?.flickr_images().let { if (it!!.isNotEmpty()) it[0] else "" },
            pictures = response?.data?.launch()?.links()?.fragments()?.linkInfo()
                ?.flickr_images()
                .let { if (it!!.isNotEmpty()) it else emptyList() },
            video = response?.data?.launch()?.links()?.fragments()?.linkInfo()?.video_link(),
        )
    }

    private fun getMission(response: Response<GetLaunchQuery.Data>?): Mission {
        return Mission(
            name = response?.data?.launch()?.fragments()?.missionDetails()?.mission_name(),
            date = response?.data?.launch()?.fragments()?.missionDetails()
                ?.launch_date_utc().toString(),
            rocketName = response?.data?.launch()?.rocket()?.fragments()?.rocketFields()
                ?.rocket_name(),
            place = response?.data?.launch()?.launch_site()?.site_name_long(),
            success = response?.data?.launch()?.fragments()?.missionDetails()?.launch_success(),
            details = response?.data?.launch()?.details()
        )
    }

    private fun getPayload(response: Response<GetLaunchQuery.Data>): Payload {
        return Payload(
            orbit = response.data?.payload()?.orbit(),
            nationality = response.data?.payload()?.nationality(),
            manufacturer = response.data?.payload()?.manufacturer(),
            customers = response.data?.payload()?.customers() ?: emptyList(),
            mass = response.data?.payload()?.payload_mass_kg(),
            reused = response.data?.payload()?.reused(),
        )
    }
}

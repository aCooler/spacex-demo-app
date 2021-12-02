package com.example.myspacexdemoapp.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apollographql.apollo.api.Response
import com.example.myspacexdemoapp.api.SpaceXApi
import com.example.myspacexdemoapp.ui.launches.LaunchUiModel
import com.example.spacexdemoapp.GetLaunchQuery

class LaunchDetailsViewModel(private val spaceXApi: SpaceXApi) : ViewModel() {


    private val _launchMutableLiveData = MutableLiveData<LaunchDetailsViewState>()
    val launchLiveData: LiveData<LaunchDetailsViewState> = _launchMutableLiveData



    fun getLaunch(id: String) {
        spaceXApi.getLaunchById(id)
            .doOnSubscribe {
                _launchMutableLiveData.postValue(LaunchDetailsViewState.Loading(id))
            }
            .subscribe({ response ->
                _launchMutableLiveData.postValue(
                    LaunchDetailsViewState.Success(
                        getLaunchUiModel(response, id),
                    )
                )

            }, { throwable ->
                _launchMutableLiveData.postValue(LaunchDetailsViewState.Error(throwable))
            })
    }



    private fun getLaunchUiModel(
        response: Response<GetLaunchQuery.Data>,
        id: String
    ): LaunchUiModel {
        val values = getLaunchUiModelValuesForDetekt(response)
        return LaunchUiModel(
            id,
            badge = values[0],
            name = values[1],
            date = values[2],
            rocketName = values[3],
            place = values[4],
            success = response.data?.launch()?.fragments()?.missionDetails()?.launch_success()
                ?: true,
            picture = response.data?.launch()?.links()?.fragments()?.linkInfo()
                ?.flickr_images().let { if (it!!.isNotEmpty()) it[0] else "" },
            details = response.data?.launch()?.details() ?: "",
            orbit = response.data?.payload()?.orbit() ?: "",
            nationality = response.data?.payload()?.nationality() ?: "",
            manufacturer = response.data?.payload()?.manufacturer() ?: "",
            customers = response.data?.payload()?.customers() ?: emptyList(),
            mass = response.data?.payload()?.payload_mass_kg() ?: 0.0,
            pictures = response.data?.launch()?.links()?.fragments()?.linkInfo()
                ?.flickr_images()
                .let { if (it!!.isNotEmpty()) it else emptyList() },
            video = response.data?.launch()?.links()?.fragments()?.linkInfo()?.video_link()
                ?: "",
            reused = response.data?.payload()?.reused(),
        )
    }

    private fun getLaunchUiModelValuesForDetekt(
        response: Response<GetLaunchQuery.Data>,
    ): List<String> {
        return listOf(
            response.data?.launch()?.links()?.fragments()?.linkInfo()
                ?.mission_patch() ?: "",
            response.data?.launch()?.fragments()?.missionDetails()?.mission_name()
                ?: "",
            response.data?.launch()?.fragments()?.missionDetails()
                ?.launch_date_utc().toString(),
            response.data?.launch()?.rocket()?.fragments()?.rocketFields()
                ?.rocket_name() ?: "",
            response.data?.launch()?.launch_site()?.site_name_long() ?: "",
        )
    }


}

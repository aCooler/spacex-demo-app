package com.example.myspacexdemoapp.ui.mappers

import com.example.myspacexdemoapp.ui.DataModel
import com.example.myspacexdemoapp.ui.launches.LaunchUiModel
import com.example.myspacexdemoapp.ui.launches.Payload

class LaunchUIMapper(private val launchUiModel: LaunchUiModel) {

    private val recycleViewModel: MutableList<DataModel> = mutableListOf()

    companion object {
        const val PAYLOAD_NAME = "Payload #1"
        const val NAME = "Name"
        const val CUSTOMER = "Customer"
        const val MANUFACTURE = "Manufacture"
        const val MASS = "Mass"
        const val NATIONALITY = "Nationalities"
        const val ORBIT = "Orbit"
        const val GALLERY = "Gallery"
    }

    fun launchUiModelToDataModel(): MutableList<DataModel> {
        addPicture()
        addLaunchEvent()
        addLaunchDetails()
        addPayload()
        addGallery()
        return recycleViewModel
    }

    private fun addGallery() {
        launchUiModel.linkInfo.pictures.let { list ->
            val dataModel = DataModel.Gallery(
                list.filterNotNull().filter
                {
                    it.isNotBlank()
                }
            )
            if (dataModel.pictures.isNotEmpty()) {
                recycleViewModel.add(DataModel.SingleString(word = GALLERY))
                recycleViewModel.add(dataModel)
            }
        }
    }

    private fun addPayload() {
        val payloadList = mutableListOf<DataModel>()
        if (launchUiModel.mission.name.isNotEmpty()) {
            payloadList.add(DataModel.TitleAndText(title = NAME, text = launchUiModel.mission.name))
        }
        if (launchUiModel.payload.customers.isNotEmpty()) {
            payloadList.add(
                DataModel.TitleAndText(
                    title = CUSTOMER,
                    text = launchUiModel.payload.customers[0]  ?: Payload.EMPTY.manufacturer
                )
            )
        }
        if (launchUiModel.payload.manufacturer.isNotEmpty()) {
            payloadList.add(
                DataModel.TitleAndText(
                    title = MANUFACTURE,
                    text = launchUiModel.payload.manufacturer
                )
            )
        }
        if (launchUiModel.payload.mass != 0.0) {
            payloadList.add(
                DataModel.TitleAndText(
                    title = MASS,
                    text = launchUiModel.payload.mass.toString()
                )
            )
        }
        if (launchUiModel.payload.nationality.isNotEmpty()) {
            payloadList.add(
                DataModel.TitleAndText(
                    title = NATIONALITY,
                    text = launchUiModel.payload.nationality
                )
            )
        }
        if (launchUiModel.payload.orbit.isNotEmpty()) {
            payloadList.add(
                DataModel.TitleAndText(
                    title = ORBIT,
                    text = launchUiModel.payload.orbit
                )
            )
        }
        if (payloadList.isNotEmpty()) {
            payloadList.add(0, DataModel.SingleString(word = PAYLOAD_NAME))
            recycleViewModel.addAll(payloadList)
        }
    }

    private fun addLaunchDetails() {
        if (launchUiModel.mission.details.isNotEmpty()) {
            recycleViewModel.add(DataModel.Details(details = launchUiModel.mission.details))
        }
    }

    private fun addLaunchEvent() {

        var listHasItems = false
        val date = if (launchUiModel.mission.date.isNotEmpty()) {
            listHasItems = true
            launchUiModel.mission.date
        } else {
            ""
        }
        val rocket = if (launchUiModel.mission.rocketName.isNotEmpty()) {
            listHasItems = true
            launchUiModel.mission.rocketName
        } else {
            ""
        }
        val place = if (launchUiModel.mission.place.isNotEmpty()) {
            listHasItems = true
            launchUiModel.mission.place
        } else {
            ""
        }
        val reused = launchUiModel.payload.reused
        if (reused) {
            listHasItems = true
        }

        val data: DataModel.LaunchEvent =
            DataModel.LaunchEvent(date = date, rocket = rocket, reused = reused, place = place)
        if (listHasItems) {
            recycleViewModel.add(data)
        }
    }

    private fun addPicture() {
        val data: DataModel.MainInfo = DataModel.MainInfo(
            pictureUrl = launchUiModel.linkInfo.picture,
            badgeUrl = launchUiModel.linkInfo.badge,
            success = launchUiModel.mission.success,
            number = launchUiModel.number
        )
        if (data.pictureUrl.isNotEmpty() || data.badgeUrl.isNotEmpty()) {
            recycleViewModel.add(data)
        }
    }
}

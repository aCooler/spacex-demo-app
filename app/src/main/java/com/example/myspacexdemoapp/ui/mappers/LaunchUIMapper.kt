package com.example.myspacexdemoapp.ui.mappers

import com.example.myspacexdemoapp.ui.DataModel
import com.example.myspacexdemoapp.ui.launches.LaunchUiModel

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
        launchUiModel.linkInfo?.pictures.let { list ->
            val dataModel = DataModel.Gallery(
                list?.filter
                {
                    it.isNotBlank()
                }
            )
            if (!dataModel.pictures.isNullOrEmpty()) {
                recycleViewModel.add(DataModel.SingleString(word = GALLERY))
                recycleViewModel.add(dataModel)
            }
        }
    }

    private fun addPayload() {
        val payloadList = mutableListOf<DataModel>()
        if (!launchUiModel.mission?.name.isNullOrEmpty()) {
            payloadList.add(DataModel.TitleAndText(title = NAME, text = launchUiModel.mission?.name))
        }
        if (!launchUiModel.payload?.customers.isNullOrEmpty()) {
            payloadList.add(
                DataModel.TitleAndText(
                    title = CUSTOMER,
                    text = launchUiModel.payload?.customers?.get(0)
                )
            )
        }
        if (!launchUiModel.payload?.manufacturer.isNullOrEmpty()) {
            payloadList.add(
                DataModel.TitleAndText(
                    title = MANUFACTURE,
                    text = launchUiModel.payload?.manufacturer
                )
            )
        }
        if (launchUiModel.payload?.mass != 0.0 && launchUiModel.payload?.mass != null) {
            payloadList.add(
                DataModel.TitleAndText(
                    title = MASS,
                    text = launchUiModel.payload.mass.toString()
                )
            )
        }
        if (!launchUiModel.payload?.nationality.isNullOrEmpty()) {
            payloadList.add(
                DataModel.TitleAndText(
                    title = NATIONALITY,
                    text = launchUiModel.payload?.nationality
                )
            )
        }
        if (!launchUiModel.payload?.orbit.isNullOrEmpty()) {
            payloadList.add(
                DataModel.TitleAndText(
                    title = ORBIT,
                    text = launchUiModel.payload?.orbit
                )
            )
        }
        if (!payloadList.isNullOrEmpty()) {
            payloadList.add(0, DataModel.SingleString(word = PAYLOAD_NAME))
            recycleViewModel.addAll(payloadList)
        }
    }

    private fun addLaunchDetails() {
        if (launchUiModel.mission?.details?.isNotEmpty() == true) {
            recycleViewModel.add(DataModel.Details(details = launchUiModel.mission.details))
        }
    }

    private fun addLaunchEvent() {

        var listIsEmpty = true
        val date = if (launchUiModel.mission?.date?.isNotEmpty() == true) {
            listIsEmpty = false
            launchUiModel.mission.date
        } else {
            null
        }
        val rocket = if (launchUiModel.mission?.rocketName?.isNotEmpty() == true) {
            listIsEmpty = false
            launchUiModel.mission.rocketName
        } else {
            null
        }
        val place = if (launchUiModel.mission?.place?.isNotEmpty() == true) {
            listIsEmpty = false
            launchUiModel.mission.place
        } else {
            null
        }
        val reused = launchUiModel.payload?.reused
        if (reused == null) {
            listIsEmpty = false
        }

        val data: DataModel.LaunchEvent =
            DataModel.LaunchEvent(date = date, rocket = rocket, reused = reused, place = place)
        if (!listIsEmpty) {
            recycleViewModel.add(data)
        }
    }

    private fun addPicture() {
        val data: DataModel.MainInfo = DataModel.MainInfo(
            pictureUrl = launchUiModel.linkInfo?.picture,
            badgeUrl = launchUiModel.linkInfo?.badge,
            success = launchUiModel.mission?.success,
            number = launchUiModel.number
        )
        if (!data.pictureUrl.isNullOrEmpty() || !data.badgeUrl.isNullOrEmpty()) {
            recycleViewModel.add(data)
        }
    }
}

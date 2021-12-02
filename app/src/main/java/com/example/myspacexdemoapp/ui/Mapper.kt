package com.example.myspacexdemoapp.ui

import com.example.myspacexdemoapp.ui.launches.LaunchUiModel

class Mapper(private val launchUiModel: LaunchUiModel) {

    private val recycleViewModel: MutableList<DataModel> = mutableListOf()

    companion object {
        const val payloadName = "Payload #1"
        const val name = "Name"
        const val customer = "Customer"
        const val manufacture = "Manufacture"
        const val mass = "Mass"
        const val nationality = "Nationalities"
        const val orbit = "Orbit"
        const val gallery = "Gallery"
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
            val dataModel = DataModel.Gallery(list?.filter {
                it.isNotBlank()
            })
            if (!dataModel.pictures.isNullOrEmpty()) {
                recycleViewModel.add(DataModel.OneWord(word = gallery))
                recycleViewModel.add(dataModel)
            }

        }
    }

    private fun addPayload() {


        val payloadList = mutableListOf<DataModel>()
        if (launchUiModel.mission?.name?.isNotEmpty() == true) {
            payloadList.add(DataModel.TitleAndText(title = name, text = launchUiModel.mission.name))
        }
        if (launchUiModel.payload?.customers?.isNotEmpty() == true) {
            payloadList.add(
                DataModel.TitleAndText(
                    title = customer,
                    text = launchUiModel.payload.customers[0]
                )
            )
        }
        if (launchUiModel.payload?.manufacturer?.isEmpty() != true) {
            payloadList.add(
                DataModel.TitleAndText(
                    title = manufacture,
                    text = launchUiModel.payload?.manufacturer
                )
            )
        }
        if (launchUiModel.payload?.mass != 0.0) {
            payloadList.add(
                DataModel.TitleAndText(
                    title = mass,
                    text = launchUiModel.payload?.mass.toString()
                )
            )
        }
        if (launchUiModel.payload?.nationality?.isNotEmpty() == true) {
            payloadList.add(
                DataModel.TitleAndText(
                    title = nationality,
                    text = launchUiModel.payload.nationality
                )
            )
        }
        if (launchUiModel.payload?.orbit?.isNotEmpty() ==true) {
            payloadList.add(DataModel.TitleAndText(title = orbit, text = launchUiModel.payload.orbit))
        }
        if (payloadList.isNotEmpty()) {
            payloadList.add(0, DataModel.OneWord(word = payloadName))
            recycleViewModel.addAll(payloadList)
        }

    }


    private fun addLaunchDetails() {
        if (launchUiModel.mission?.details?.isNotEmpty()==true) {
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
        val data: DataModel.Picture = DataModel.Picture(
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

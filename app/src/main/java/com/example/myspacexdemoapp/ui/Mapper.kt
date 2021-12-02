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
        launchUiModel.pictures.let { list ->
            val dataModel = DataModel.Gallery(list.filter {
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
        if (launchUiModel.name.isNotEmpty()) {
            payloadList.add(DataModel.TitleAndText(title = name, text = launchUiModel.name))
        }
        if (launchUiModel.customers.isNotEmpty()) {
            payloadList.add(
                DataModel.TitleAndText(
                    title = customer,
                    text = launchUiModel.customers[0]
                )
            )
        }
        if (launchUiModel.manufacturer.isNotEmpty()) {
            payloadList.add(
                DataModel.TitleAndText(
                    title = manufacture,
                    text = launchUiModel.manufacturer
                )
            )
        }
        if (launchUiModel.mass != 0.0) {
            payloadList.add(
                DataModel.TitleAndText(
                    title = mass,
                    text = launchUiModel.mass.toString()
                )
            )
        }
        if (launchUiModel.nationality.isNotEmpty()) {
            payloadList.add(
                DataModel.TitleAndText(
                    title = nationality,
                    text = launchUiModel.nationality
                )
            )
        }
        if (launchUiModel.orbit.isNotEmpty()) {
            payloadList.add(DataModel.TitleAndText(title = orbit, text = launchUiModel.orbit))
        }
        if (payloadList.isNotEmpty()) {
            payloadList.add(0, DataModel.OneWord(word = payloadName))
            recycleViewModel.addAll(payloadList)
        }

    }


    private fun addLaunchDetails() {
        if (launchUiModel.details.isNotEmpty()) {
            recycleViewModel.add(DataModel.Details(details = launchUiModel.details))
        }
    }

    private fun addLaunchEvent() {
        val data: DataModel.LaunchEvent = DataModel.LaunchEvent()

        var listIsEmpty = true
        if (launchUiModel.date.isNotEmpty()) {
            data.date = launchUiModel.date
            listIsEmpty = false
        }
        if (launchUiModel.rocketName.isNotEmpty()) {
            data.rocket = launchUiModel.rocketName
            listIsEmpty = false
        }
        if (launchUiModel.place.isNotEmpty()) {
            data.place = launchUiModel.place
            listIsEmpty = false
        }
        if (launchUiModel.reused) {
            data.reused = launchUiModel.reused
            listIsEmpty = false
        }
        if (!listIsEmpty) {
            recycleViewModel.add(data)
        }


    }

    private fun addPicture() {
        val data: DataModel.Picture = DataModel.Picture()
        if (launchUiModel.number.isNotEmpty()) {
            data.number = launchUiModel.number
        }
        if (launchUiModel.picture.isNotEmpty()) {
            data.picture_url = launchUiModel.picture
        }
        if (launchUiModel.badge.isNotEmpty()) {
            data.badge_url = launchUiModel.badge
        }
        data.success = launchUiModel.success
        if (!data.picture_url.isNullOrEmpty() || !data.badge_url.isNullOrEmpty()) {
            recycleViewModel.add(data)
        }
    }
}

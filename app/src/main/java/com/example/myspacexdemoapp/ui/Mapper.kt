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

        var listIsEmpty = true
        val date = if (launchUiModel.date.isNotEmpty()) {
            listIsEmpty = false
            launchUiModel.date
        } else {
            null
        }
        val rocket = if (launchUiModel.rocketName.isNotEmpty()) {
            listIsEmpty = false
            launchUiModel.rocketName
        } else {
            null
        }
        val place = if (launchUiModel.place.isNotEmpty()) {
            listIsEmpty = false
            launchUiModel.place
        } else {
            null
        }
        val reused = launchUiModel.reused
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
            pictureUrl = launchUiModel.picture,
            badgeUrl = launchUiModel.badge,
            success = launchUiModel.success,
            number = launchUiModel.number
        )
        if (!data.pictureUrl.isNullOrEmpty() || !data.badgeUrl.isNullOrEmpty()) {
            recycleViewModel.add(data)
        }
    }
}

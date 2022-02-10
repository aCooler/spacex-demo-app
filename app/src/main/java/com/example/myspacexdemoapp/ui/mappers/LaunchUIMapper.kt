package com.example.myspacexdemoapp.ui.mappers

import com.example.domain.HomeData
import com.example.domain.LaunchData
import com.example.domain.Payload
import com.example.domain.toDateString
import com.example.myspacexdemoapp.ui.UIModel
import com.example.myspacexdemoapp.ui.start.StartUIModel

class LaunchUIMapper(private val launchData: LaunchData) {

    private val recycleViewModel: MutableList<UIModel> = mutableListOf()

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

    fun launchUiModelToDataModel(): MutableList<UIModel> {
        addPicture()
        addLaunchEvent()
        addLaunchDetails()
        addPayload()
        addGallery()
        addYoutube()
        return recycleViewModel
    }

    private fun addGallery() {
        launchData.linkInfo.pictures.let { list ->
            val dataModel = UIModel.Gallery(
                list.filterNotNull().filter
                {
                    it.isNotBlank()
                }
            )
            if (dataModel.pictures.isNotEmpty()) {
                recycleViewModel.add(UIModel.SingleString(word = GALLERY))
                recycleViewModel.add(dataModel)
            }
        }
    }

    private fun addYoutube() {
        val (_, urlSplit) = launchData.linkInfo.video.split("v=")
        val dataModel = UIModel.Youtube(
            id = urlSplit
        )
        if (urlSplit.length > 1) {
            recycleViewModel.add(dataModel)
        }
    }

    private fun addPayload() {
        val payloadList = mutableListOf<UIModel>()
        if (launchData.mission.name.isNotEmpty()) {
            payloadList.add(UIModel.TitleAndText(title = NAME, text = launchData.mission.name))
        }
        if (launchData.payload.customers.isNotEmpty()) {
            payloadList.add(
                UIModel.TitleAndText(
                    title = CUSTOMER,
                    text = launchData.payload.customers[0] ?: Payload.EMPTY.manufacturer
                )
            )
        }
        if (launchData.payload.manufacturer.isNotEmpty()) {
            payloadList.add(
                UIModel.TitleAndText(
                    title = MANUFACTURE,
                    text = launchData.payload.manufacturer
                )
            )
        }
        if (launchData.payload.mass != 0.0) {
            payloadList.add(
                UIModel.TitleAndText(
                    title = MASS,
                    text = launchData.payload.mass.toString()
                )
            )
        }
        if (launchData.payload.nationality.isNotEmpty()) {
            payloadList.add(
                UIModel.TitleAndText(
                    title = NATIONALITY,
                    text = launchData.payload.nationality
                )
            )
        }
        if (launchData.payload.orbit.isNotEmpty()) {
            payloadList.add(
                UIModel.TitleAndText(
                    title = ORBIT,
                    text = launchData.payload.orbit
                )
            )
        }
        if (payloadList.isNotEmpty()) {
            payloadList.add(0, UIModel.SingleString(word = PAYLOAD_NAME))
            recycleViewModel.addAll(payloadList)
        }
    }

    private fun addLaunchDetails() {
        if (launchData.mission.details.isNotEmpty()) {
            recycleViewModel.add(UIModel.Details(details = launchData.mission.details))
        }
    }

    private fun addLaunchEvent() {

        var listHasItems = false
        val date = if (launchData.mission.date.toDateString().isNotEmpty()) {
            listHasItems = true
            launchData.mission.date
        } else {
            ""
        }
        val rocket = if (launchData.mission.rocketName.isNotEmpty()) {
            listHasItems = true
            launchData.mission.rocketName
        } else {
            ""
        }
        val place = if (launchData.mission.place.isNotEmpty()) {
            listHasItems = true
            launchData.mission.place
        } else {
            ""
        }
        val reused = launchData.payload.reused
        if (reused) {
            listHasItems = true
        }
        val ui: UIModel.LaunchEvent =
            UIModel.LaunchEvent(
                date = date.toString(),
                rocket = rocket,
                reused = reused,
                place = place
            )
        if (listHasItems) {
            recycleViewModel.add(ui)
        }
    }

    private fun addPicture() {
        val ui: UIModel.MainInfo = UIModel.MainInfo(
            pictureUrl = launchData.linkInfo.picture,
            badgeUrl = launchData.linkInfo.badge,
            success = launchData.mission.success,
            number = launchData.number
        )
        if (ui.pictureUrl.isNotEmpty() || ui.badgeUrl.isNotEmpty()) {
            recycleViewModel.add(ui)
        }
    }
}

fun HomeData.toTimerUIList(): List<StartUIModel> {
    val nextLaunch = this.launchData
    val rocketsEfficiency = this.rockets
    val timeToLaunch = nextLaunch.mission.date
    val dataset: MutableList<StartUIModel> = mutableListOf()
    dataset.apply {
        add(
            StartUIModel.Timer(
                name = nextLaunch.mission.name,
                days = timeToLaunch.day.toString(),
                hours = timeToLaunch.hours.toString(),
                minutes = timeToLaunch.minutes.toString(),
                seconds = timeToLaunch.seconds.toString()
            )
        )
        add(
            StartUIModel.Launches(
                successful = rocketsEfficiency.efficiency,
                total = rocketsEfficiency.total,
                efficiency = "",
                toLaunches = ""
            )
        )
        add(
            StartUIModel.Rockets(
                tweet = ""
            )
        )
    }
    return dataset
}

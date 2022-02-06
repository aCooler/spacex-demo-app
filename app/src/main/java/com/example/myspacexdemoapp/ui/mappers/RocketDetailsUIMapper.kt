package com.example.myspacexdemoapp.ui.mappers

import com.example.domain.RocketDetailsData
import com.example.domain.RocketSingleValue
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.ui.rocketDetails.RocketDetailsUIModel

class RocketDetailsUIMapper(private val rocketData: RocketDetailsData) {

    private val recycleViewModel: MutableList<RocketDetailsUIModel> = mutableListOf()

    fun rocketDetailsUiModelToDataModel(): MutableList<RocketDetailsUIModel> {
        addPicture()
        addLaunchDetails()
        addDetails()
        addExpandableList()
        return recycleViewModel
    }

    private fun addExpandableList() {
        rocketData.expandables.forEachIndexed { index, expandable ->
            addTopExpandable(expandable.topItem, index)
            expandable.itemList.map {
                addTopItem(it.topItem,index)
                addTitleAndTextItem(it.itemList,index)
            }
        }
    }

    private fun addTitleAndTextItem(itemList: Map<String, String>, index: Int) {
        itemList.map {
            val item = RocketDetailsUIModel.TitleAndText(
                title = it.key,
                text = it.value,
                position = index
            )
            recycleViewModel.add(item)
        }
    }

    private fun addTopItem(topItem: RocketSingleValue, index: Int) {
        if (topItem.word.isNotEmpty()) {
            val rowPlane = RocketDetailsUIModel.Row(
                word = topItem.word, position = index)
            recycleViewModel.add(rowPlane)
        }
    }

    private fun addTopExpandable(topItem: RocketSingleValue, number: Int) {
        val rowExpandable = RocketDetailsUIModel.RowExpandable(word = topItem.word, position = number)
        recycleViewModel.add(rowExpandable)
    }

    private fun addLaunchDetails() {
        if (rocketData.basics.firstFlight.word.isNotEmpty()) {
            val firstFlight = RocketDetailsUIModel.IconAndText(
                icon = R.drawable.ic_calendar,
                text = rocketData.basics.firstFlight.word
            )
            recycleViewModel.add(firstFlight)
        }

        if (rocketData.basics.country.word.isNotEmpty()) {
            val country = RocketDetailsUIModel.IconAndText(
                icon = R.drawable.ic_flag,
                text = rocketData.basics.country.word
            )
            recycleViewModel.add(country)
        }
        if (rocketData.basics.stages.word.isNotEmpty()) {
            val stages = RocketDetailsUIModel.IconAndText(
                icon = R.drawable.icon,
                text = rocketData.basics.stages.word
            )
            recycleViewModel.add(stages)
        }
        if (rocketData.basics.cost.word.isNotEmpty()) {
            val cost = RocketDetailsUIModel.IconAndText(
                icon = R.drawable.ic_currency_usd,
                text = rocketData.basics.cost.word
            )
            recycleViewModel.add(cost)
        }
    }

    private fun addPicture() {
        val ui: RocketDetailsUIModel.MainInfo = RocketDetailsUIModel.MainInfo(
            pictureUrl = rocketData.linkInfo.picture,
            success = rocketData.mission.success,
        )
        if (ui.pictureUrl.isNotEmpty()) {
            recycleViewModel.add(ui)
        }
    }

    private fun addDetails() {
        if (rocketData.mission.details.isNotEmpty()) {
            recycleViewModel.add(RocketDetailsUIModel.Details(details = rocketData.mission.details))
        }
    }
}

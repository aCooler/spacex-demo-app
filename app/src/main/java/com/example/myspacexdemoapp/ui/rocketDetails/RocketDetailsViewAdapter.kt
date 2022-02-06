package com.example.myspacexdemoapp.ui.rocketDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.databinding.DetailsBinding
import com.example.myspacexdemoapp.databinding.IconAndTextBinding
import com.example.myspacexdemoapp.databinding.RocketDetailsPictureBinding
import com.example.myspacexdemoapp.databinding.RowExpandableBinding
import com.example.myspacexdemoapp.databinding.RowRocketsDetailsBinding
import com.example.myspacexdemoapp.databinding.SingleBinding
import com.example.myspacexdemoapp.ui.launch.adapters.RocketDetailsViewHolder
import com.example.myspacexdemoapp.ui.launch.adapters.RocketPictureViewHolder
import com.example.myspacexdemoapp.ui.launch.adapters.RocketRowViewHolder
import com.example.myspacexdemoapp.ui.launch.adapters.RocketSingleViewHolder
import com.example.myspacexdemoapp.ui.rocketDetails.adapters.ExpandableRowViewHolder
import com.example.myspacexdemoapp.ui.rocketDetails.adapters.IconAndTestViewHolder

class RocketDetailsViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfData: MutableList<RocketDetailsUIModel> = mutableListOf()
    private var basicListOfData: MutableList<RocketDetailsUIModel> = mutableListOf()
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val launchRocketDetailsUIModel = listOfData[position]
        when (holder) {
            is RocketPictureViewHolder -> holder.onBindView(launchRocketDetailsUIModel as RocketDetailsUIModel.MainInfo)
            is RocketDetailsViewHolder -> holder.onBindView(launchRocketDetailsUIModel as RocketDetailsUIModel.Details)
            is IconAndTestViewHolder -> holder.onBindView(launchRocketDetailsUIModel as RocketDetailsUIModel.IconAndText)
            is ExpandableRowViewHolder -> holder.onBindView(launchRocketDetailsUIModel as RocketDetailsUIModel.RowExpandable)
            is RocketRowViewHolder -> holder.onBindView(launchRocketDetailsUIModel as RocketDetailsUIModel.Row)
            is RocketSingleViewHolder -> holder.onBindView(launchRocketDetailsUIModel as RocketDetailsUIModel.TitleAndText)
        }
    }

    override fun getItemViewType(position: Int) = when (listOfData[position]) {
        is RocketDetailsUIModel.MainInfo -> R.layout.rocket_details_picture
        is RocketDetailsUIModel.Details -> R.layout.details
        is RocketDetailsUIModel.IconAndText -> R.layout.icon_and_text
        is RocketDetailsUIModel.RowExpandable -> R.layout.row_expandable
        is RocketDetailsUIModel.Row -> R.layout.row_rockets_details
        is RocketDetailsUIModel.TitleAndText -> R.layout.single
    }

    override fun getItemCount(): Int = listOfData.size

    fun setItems(model: MutableList<RocketDetailsUIModel>) {
        basicListOfData = model
        listOfData = model
        notifyItemRangeChanged(0, listOfData.size)
    }

    fun itemClicked(position: Int) {
        val expanded = listOfData.any {
            when (it) {
                is RocketDetailsUIModel.Row -> it.position == position
                is RocketDetailsUIModel.TitleAndText -> it.position == position
                else -> false
            }
        }
        if (expanded) {
            collapseList(position)
        } else {
            expandList(position)
        }
    }

    private fun expandList(position: Int) {
        val collapsedList = basicListOfData.filter {
            when (it) {
                is RocketDetailsUIModel.Row -> it.position == position
                is RocketDetailsUIModel.TitleAndText -> it.position == position
                else -> false
            }
        }
        var expandablePosition = 0
        listOfData.forEachIndexed { n, model ->
            if (model is RocketDetailsUIModel.RowExpandable) {
                if (model.position == position) {
                    expandablePosition = n
                }
            }
        }
        listOfData.addAll(expandablePosition + 1, collapsedList)
        notifyItemRangeInserted(expandablePosition + 1, collapsedList.size)
    }

    private fun collapseList(position: Int) {
        var counter = 0
        var firstPosition = 0
        listOfData.forEachIndexed { index, uiModel ->
            when {
                uiModel is RocketDetailsUIModel.Row && uiModel.position == position -> {
                    if (firstPosition == 0)
                        firstPosition = index
                    counter++
                }
                uiModel is RocketDetailsUIModel.TitleAndText && uiModel.position == position -> {
                    if (firstPosition == 0)
                        firstPosition = index
                    counter++
                }
            }
        }

        listOfData = listOfData.filterNot {
            when (it) {
                is RocketDetailsUIModel.Row -> it.position == position
                is RocketDetailsUIModel.TitleAndText -> it.position == position
                else -> false
            }
        }.toMutableList()
        notifyItemRangeRemoved(firstPosition, counter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.rocket_details_picture -> RocketPictureViewHolder(
                RocketDetailsPictureBinding.bind(view)
            )
            R.layout.details -> RocketDetailsViewHolder(DetailsBinding.bind(view))
            R.layout.icon_and_text -> IconAndTestViewHolder(IconAndTextBinding.bind(view))
            R.layout.row_expandable -> ExpandableRowViewHolder(RowExpandableBinding.bind(view)) { n ->
                itemClicked(position = n)
            }
            R.layout.row_rockets_details -> RocketRowViewHolder(RowRocketsDetailsBinding.bind(view))
            R.layout.single -> RocketSingleViewHolder(SingleBinding.bind(view))
            else -> RocketSingleViewHolder(SingleBinding.bind(view))
        }
    }
}

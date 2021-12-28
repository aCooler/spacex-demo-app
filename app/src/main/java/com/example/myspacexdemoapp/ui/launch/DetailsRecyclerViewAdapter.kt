package com.example.myspacexdemoapp.ui.launch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.databinding.DetailsBinding
import com.example.myspacexdemoapp.databinding.GalleryBinding
import com.example.myspacexdemoapp.databinding.MissionCardBinding
import com.example.myspacexdemoapp.databinding.PictureBinding
import com.example.myspacexdemoapp.databinding.RowBinding
import com.example.myspacexdemoapp.databinding.SingleBinding
import com.example.myspacexdemoapp.ui.DataModel
import com.example.myspacexdemoapp.ui.launch.adapters.CardViewHolder
import com.example.myspacexdemoapp.ui.launch.adapters.DetailsViewHolder
import com.example.myspacexdemoapp.ui.launch.adapters.GalleryViewHolder
import com.example.myspacexdemoapp.ui.launch.adapters.PictureViewHolder
import com.example.myspacexdemoapp.ui.launch.adapters.RowViewHolder
import com.example.myspacexdemoapp.ui.launch.adapters.SingleViewHolder

class DetailsRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfData: MutableList<DataModel> = mutableListOf()
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val launchUIModel = listOfData[position]
        when (holder) {
            is CardViewHolder -> holder.onBindView(launchUIModel as DataModel.LaunchEvent)
            is PictureViewHolder -> holder.onBindView(launchUIModel as DataModel.MainInfo)
            is DetailsViewHolder -> holder.onBindView(launchUIModel as DataModel.Details)
            is RowViewHolder -> holder.onBindView(launchUIModel as DataModel.SingleString)
            is SingleViewHolder -> holder.onBindView(launchUIModel as DataModel.TitleAndText)
            is GalleryViewHolder -> holder.onBindView(launchUIModel as DataModel.Gallery)
        }
    }

    override fun getItemViewType(position: Int) = when (listOfData[position]) {
        is DataModel.MainInfo -> R.layout.picture
        is DataModel.LaunchEvent -> R.layout.mission_card
        is DataModel.Details -> R.layout.details
        is DataModel.SingleString -> R.layout.row
        is DataModel.TitleAndText -> R.layout.single
        is DataModel.Gallery -> R.layout.gallery
    }

    override fun getItemCount(): Int = listOfData.size

    fun setItems(model: MutableList<DataModel>) {
        listOfData = model
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.picture -> PictureViewHolder(PictureBinding.bind(view))
            R.layout.mission_card -> CardViewHolder(MissionCardBinding.bind(view))
            R.layout.details -> DetailsViewHolder(DetailsBinding.bind(view))
            R.layout.single -> SingleViewHolder(SingleBinding.bind(view))
            R.layout.gallery -> GalleryViewHolder(GalleryBinding.bind(view))
            R.layout.row -> RowViewHolder(RowBinding.bind(view))
            else -> CardViewHolder(MissionCardBinding.bind(view))
        }
    }
}

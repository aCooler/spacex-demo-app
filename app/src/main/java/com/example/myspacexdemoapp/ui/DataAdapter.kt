package com.example.myspacexdemoapp.ui

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.api.toDateString
import com.example.myspacexdemoapp.ui.launches.LaunchUiModel

class DataAdapter : RecyclerView.Adapter<DataAdapter.DataAdapterViewHolder>() {

    private val adapterData = mutableListOf<DataModel>()

    //--------onCreateViewHolder: inflate layout with view holder-------
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapterViewHolder {

        val layout = when (viewType) {
            TYPE_PICTURE -> R.layout.card
            TYPE_CARD -> R.layout.picture
            TYPE_DETAILS -> R.layout.details
            TYPE_SINGLE -> R.layout.single
            TYPE_GALLERY -> R.layout.gallery
            else -> throw IllegalArgumentException("Invalid view type")
        }

        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)

        return DataAdapterViewHolder(view)
    }


    //-----------onBindViewHolder: bind view with data model---------
    override fun onBindViewHolder(holder: DataAdapterViewHolder, position: Int) {
        if(adapterData.isEmpty()) {
            holder.bind(DataModel.Card(LaunchUiModel()))
        }
        else{
            holder.bind(adapterData[position])
        }

    }

    override fun getItemCount(): Int = 5

    override fun getItemViewType(position: Int): Int {
        if(adapterData.isEmpty()){return TYPE_CARD }
        return when (adapterData[position]) {
            is DataModel.Picture -> TYPE_PICTURE
            is DataModel.Card -> TYPE_CARD
            is DataModel.Details -> TYPE_DETAILS
            is DataModel.Single -> TYPE_SINGLE
            is DataModel.Gallery -> TYPE_GALLERY
        }
    }

    fun setItems(data: LaunchUiModel) {
        adapterData.apply {
            clear()
            addAll(listOf(
                DataModel.Picture(data),
                DataModel.Card(data),
                DataModel.Details(data),
                DataModel.Single(data),
                DataModel.Gallery(data)
            ))
        }
    }

    companion object {
        private const val TYPE_PICTURE = 0
        private const val TYPE_CARD = 1
        private const val TYPE_DETAILS = 2
        private const val TYPE_SINGLE = 3
        private const val TYPE_GALLERY = 4

    }

    class DataAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private fun bindFamily(item: DataModel.Picture) {
            //Do your view assignment here from the data model
            val launchUiModel = item.launchUiModel
            val place: TextView
            val rocketName: TextView
            val success: TextView
            val missionName: TextView
            val date: TextView
            val number: TextView
            val picture: ImageView
            val badge: ImageView
            place = itemView.findViewById(R.id.place)
            rocketName = itemView.findViewById(R.id.rocket_name)
            missionName = itemView.findViewById(R.id.single_title)
            date = itemView.findViewById(R.id.date)
            success = itemView.findViewById(R.id.success)
            number = itemView.findViewById(R.id.number)
            picture = itemView.findViewById(R.id.launch_image)
            badge = itemView.findViewById(R.id.badge)
            place.text = item.launchUiModel.place
            rocketName.text = item.launchUiModel.rocketName
            missionName.text = item.launchUiModel.name
            date.text = launchUiModel.date.toDateString()
            number.text = String.format(
                itemView.context.getString(R.string.number),
                launchUiModel.number
            )

            if (launchUiModel.picture.isNotEmpty()) {
                Glide.with(itemView)
                    .load(picture)
                    .into(
                        picture
                    )
            }


            if (launchUiModel.badge.isNotEmpty()) {
                Glide.with(itemView)
                    .load(launchUiModel.badge)
                    .into(
                        badge
                    )
                badge.visibility = View.VISIBLE
            }

            success.text = when (launchUiModel.success) {
                true -> {

                    val green = itemView.context.resources.getColor(R.color.success_green)
                    success.setTextColor(green)
                    success.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_check,
                        0,
                        0,
                        0
                    )
                    success.compoundDrawables[0].colorFilter =
                        PorterDuffColorFilter(green, PorterDuff.Mode.SRC_IN)
                    itemView.context.getString(R.string.success)
                }


                else -> {
                    val red = itemView.context.resources.getColor(R.color.failed_red)
                    success.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_report,
                        0,
                        0,
                        0
                    )
                    success.compoundDrawables[0].colorFilter =
                        PorterDuffColorFilter(red, PorterDuff.Mode.SRC_IN)
                    success.setTextColor(red)
                    itemView.context.getString(R.string.failed)
                }
            }
        }

        private fun bindFriend(item: DataModel.Card) {
            //Do your view assignment here from the data model
            val launchUiModel = item.launchUiModel
            val success: TextView
            val number: TextView
            val picture: ImageView
            val badge: ImageView
            success = itemView.findViewById(R.id.success)
            number = itemView.findViewById(R.id.number)
            picture = itemView.findViewById(R.id.launch_image)
            badge = itemView.findViewById(R.id.badge)
            number.text = String.format(
                itemView.context.getString(R.string.number),
                launchUiModel.number
            )

            if (launchUiModel.picture.isNotEmpty()) {
                Glide.with(itemView)
                    .load(picture)
                    .into(
                        picture
                    )
            }


            if (launchUiModel.badge.isNotEmpty()) {
                Glide.with(itemView)
                    .load(launchUiModel.badge)
                    .into(
                        badge
                    )
                badge.visibility = View.VISIBLE
            }

            success.text = when (launchUiModel.success) {
                true -> {

                    val green = itemView.context.resources.getColor(R.color.success_green)
                    success.setTextColor(green)
                    success.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_check,
                        0,
                        0,
                        0
                    )
                    success.compoundDrawables[0].colorFilter =
                        PorterDuffColorFilter(green, PorterDuff.Mode.SRC_IN)
                    itemView.context.getString(R.string.success)
                }


                else -> {
                    val red = itemView.context.resources.getColor(R.color.failed_red)
                    success.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_report,
                        0,
                        0,
                        0
                    )
                    success.compoundDrawables[0].colorFilter =
                        PorterDuffColorFilter(red, PorterDuff.Mode.SRC_IN)
                    success.setTextColor(red)
                    itemView.context.getString(R.string.failed)
                }
            }
        }

        private fun bindColleague(item: DataModel.Details) {
            //Do your view assignment here from the data model
            val launchUiModel = item.launchUiModel
            val details: TextView
            details = itemView.findViewById(R.id.details)
            details.text = launchUiModel.details

        }

        private fun bindHeader(item: DataModel.Single) {
            //Do your view assignment here from the data model
            val launchUiModel = item.launchUiModel
            val text: TextView
            text = itemView.findViewById(R.id.single_text)
            text.text = launchUiModel.manufacturer
            val title: TextView
            title = itemView.findViewById(R.id.single_title)
            title.text = launchUiModel.details

        }

        private fun bindHeader(item: DataModel.Gallery) {
            //Do your view assignment here from the data model
            val launchUiModel = item.launchUiModel
            val picture: ImageView
            picture = itemView.findViewById(R.id.picture)
            Glide.with(itemView)
                .load(launchUiModel.pictures[0])
                .into(
                    picture
                )
        }

        fun bind(dataModel: DataModel) {
            when (dataModel) {
                is DataModel.Picture -> bindFamily(dataModel)
                is DataModel.Card -> bindFriend(dataModel)
                is DataModel.Details -> bindColleague(dataModel)
                is DataModel.Single -> bindHeader(dataModel)
                is DataModel.Gallery -> bindHeader(dataModel)
                else -> bindFriend(dataModel as DataModel.Card)
            }
        }
    }
}
package com.example.myspacexdemoapp.ui

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.api.toDateString
import com.example.myspacexdemoapp.ui.launches.LaunchUiModel

class DataAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var launchUIModel: LaunchUiModel = LaunchUiModel()


    fun setItems(model: LaunchUiModel) {
        launchUIModel = model
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val v = layoutInflater.inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.picture -> PictureViewHolder(v)
            R.layout.card -> CardViewHolder(v)
            R.layout.details -> DetailsViewHolder(v)
            R.layout.single -> SingleViewHolder(v)
            R.layout.gallery -> CardViewHolder(v)
            R.layout.row -> RowViewHolder(v)
            else -> CardViewHolder(v)
        }
    }

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val place: TextView
        val rocketName: TextView
        val success: TextView
        val missionName: TextView
        val date: TextView
        val number: TextView
        val picture: ImageView
        val badge: ImageView


        fun onBindView(model: LaunchUiModel) {
            itemView.setOnClickListener {}
            place.text = model.place
            rocketName.text = model.rocketName
            missionName.text = model.rocketName
            date.text = model.date.toDateString()
            number.text = String.format(
                itemView.context.getString(R.string.number),
                model.number
            )

            if (model.picture.isNotEmpty()) {
                Glide.with(itemView)
                    .load(model.picture)
                    .into(
                        picture
                    )
            }


            if (model.badge.isNotEmpty()) {
                Glide.with(itemView)
                    .load(model.badge)
                    .into(
                        badge
                    )
                badge.visibility = View.VISIBLE
            }

            val drawable: Drawable?
            drawable =
                itemView.context!!.resources.getDrawable(
                    R.drawable.ic_check,
                    itemView.context!!.theme
                )

            success.text = when (model.success) {
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
//                success.compoundDrawables[0].setBounds(0, 0, 0 + success.compoundDrawables[0].intrinsicWidth, 0
//                        + success.compoundDrawables[0].intrinsicHeight);


                    success.setTextColor(red)
                    itemView.context.getString(R.string.failed)
                }
            }

        }

        init {
            place = view.findViewById(R.id.place)
            rocketName = view.findViewById(R.id.rocket_name)
            missionName = view.findViewById(R.id.row)
            date = view.findViewById(R.id.single_title)
            success = view.findViewById(R.id.success)
            number = view.findViewById(R.id.number)
            picture = view.findViewById(R.id.launch_image)
            badge = view.findViewById(R.id.badge)
        }

    }

    class DetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val details: TextView


        fun onBindView(model: LaunchUiModel) {
            itemView.setOnClickListener {}
            details.text = model.details
        }
        init {
            details = view.findViewById(R.id.details)
        }
    }
    class RowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val row: TextView


        fun onBindView(model: LaunchUiModel) {
            itemView.setOnClickListener {}
            row.text = "Row"
        }
        init {
            row = view.findViewById(R.id.row)
        }
    }

    class SingleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val singleTitle: TextView
        val singleText: TextView


        fun onBindView(model: LaunchUiModel) {
            itemView.setOnClickListener {}
            singleTitle.text = "Start"
            singleText.text = model.place

        }
        init {
            singleTitle = view.findViewById(R.id.single_title)
            singleText = view.findViewById(R.id.single_text)
        }
    }


    class PictureViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val success: TextView
        val number: TextView
        val picture: ImageView
        val badge: ImageView


        fun onBindView(model: LaunchUiModel) {
            itemView.setOnClickListener {}
            number.text = String.format(
                itemView.context.getString(R.string.number),
                model.number
            )

            if (model.picture.isNotEmpty()) {
                Glide.with(itemView)
                    .load(model.picture)
                    .into(
                        picture
                    )
            }


            if (model.badge.isNotEmpty()) {
                Glide.with(itemView)
                    .load(model.badge)
                    .into(
                        badge
                    )
                badge.visibility = View.VISIBLE
            }

            val drawable: Drawable?
            drawable =
                itemView.context!!.resources.getDrawable(
                    R.drawable.ic_check,
                    itemView.context!!.theme
                )

            success.text = when (model.success) {
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
//                success.compoundDrawables[0].setBounds(0, 0, 0 + success.compoundDrawables[0].intrinsicWidth, 0
//                        + success.compoundDrawables[0].intrinsicHeight);
                    success.setTextColor(red)
                    itemView.context.getString(R.string.failed)
                }
            }
        }

        init {
            success = view.findViewById(R.id.success)
            number = view.findViewById(R.id.number)
            picture = view.findViewById(R.id.launch_image)
            badge = view.findViewById(R.id.badge)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is CardViewHolder -> holder.onBindView(launchUIModel)
            is PictureViewHolder -> holder.onBindView(launchUIModel)
            is DetailsViewHolder -> holder.onBindView(launchUIModel)
            is RowViewHolder -> holder.onBindView(launchUIModel)
            is SingleViewHolder -> holder.onBindView(launchUIModel)
        }
    }

    override fun getItemViewType(position: Int) = when (position) {
        0 -> R.layout.picture
        1 -> R.layout.card
        2 -> R.layout.details
        3 -> R.layout.single
        4 -> R.layout.row
        5 -> R.layout.card
        //2 -> R.layout.details
        //3 -> R.layout.single
        //4 -> R.layout.gallery
        //5 -> R.layout.row
        else -> throw IllegalStateException("Unknown view")
    }

    override fun getItemCount(): Int = if (launchUIModel.number.isEmpty()) {
        0
    } else {
        6
    }

}
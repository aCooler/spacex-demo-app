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

class RecyclerViewAdapter :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private var items: List<LaunchUiModel> = listOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val place: TextView
        val rocketName: TextView
        val successText: TextView
        val missionName: TextView
        val date: TextView
        val number: TextView
        val picture: ImageView
        val badge: ImageView
        val successIcon: ImageView

        init {
            place = view.findViewById(R.id.place)
            rocketName = view.findViewById(R.id.rocket_name)
            missionName = view.findViewById(R.id.mission_name)
            date = view.findViewById(R.id.date)
            successText = view.findViewById(R.id.success)
            number = view.findViewById(R.id.number)
            picture = view.findViewById(R.id.launch_image)
            badge = view.findViewById(R.id.badge)
            successIcon = view.findViewById(R.id.success_icon)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.launch_card, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.place.text = items[position].place
        viewHolder.rocketName.text = items[position].rocketName
        viewHolder.missionName.text = items[position].name
        viewHolder.date.text = items[position].date.toDateString() ?: ""
        viewHolder.number.text = String.format(
            viewHolder.itemView.context.getString(R.string.number),
            items[position].number
        )

        if (items[position].picture.isNotEmpty()) {
            Glide.with(viewHolder.itemView)
                .load(items[position].picture)
                .into(
                    viewHolder.picture
                )
        }

        if (items[position].badge.isNotEmpty()) {
            Glide.with(viewHolder.itemView)
                .load(items[position].badge)
                .into(
                    viewHolder.badge
                )
            viewHolder.badge.visibility = View.VISIBLE
        }

        viewHolder.successText.text = when (items[position].success) {
            true -> {
                val green = viewHolder.itemView.context.resources.getColor(R.color.success_green)
                viewHolder.successText.setTextColor(green)
                val drawable: Drawable? =
                    viewHolder.itemView.context!!.resources.getDrawable(
                        R.drawable.ic_check,
                        viewHolder.itemView.context!!.theme
                    )

                drawable?.colorFilter =
                    PorterDuffColorFilter(green, PorterDuff.Mode.SRC_IN)
                viewHolder.successIcon.setImageDrawable(
                    drawable

                )
                viewHolder.itemView.context.getString(R.string.success)
            }
            else -> {
                val red = viewHolder.itemView.context.resources.getColor(R.color.failed_red)
                val drawable: Drawable? =
                    viewHolder.itemView.context!!.resources.getDrawable(
                        R.drawable.ic_report,
                        viewHolder.itemView.context!!.theme
                    )
                drawable?.colorFilter =
                    PorterDuffColorFilter(red, PorterDuff.Mode.SRC_IN)
                viewHolder.successIcon.setImageDrawable(
                    drawable
                )
                viewHolder.successText.setTextColor(red)
                viewHolder.itemView.context.getString(R.string.failed)
            }
        }
    }
    fun setItems(strings: List<LaunchUiModel>) {
        items = strings
        notifyDataSetChanged()
    }
    override fun getItemCount() = items.size
}

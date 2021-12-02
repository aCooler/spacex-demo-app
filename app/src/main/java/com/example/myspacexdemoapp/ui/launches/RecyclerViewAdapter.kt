package com.example.myspacexdemoapp.ui.launches

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.api.toDateString

class RecyclerViewAdapter(private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<RecyclerViewAdapter.MainListViewHolder>() {
    private var items: List<LaunchUiModel> = emptyList()


    class MainListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val place: TextView = view.findViewById(R.id.place)
        val rocketName: TextView = view.findViewById(R.id.rocket_name)
        val success: TextView = view.findViewById(R.id.success)
        val successImage: ImageView = view.findViewById(R.id.success_icon)
        val missionName: TextView = view.findViewById(R.id.mission_name)
        val date: TextView = view.findViewById(R.id.date)
        val number: TextView = view.findViewById(R.id.number)
        val picture: ImageView = view.findViewById(R.id.launch_image)
        val badge: ImageView = view.findViewById(R.id.badge)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MainListViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.main_list_card, viewGroup, false)
        return MainListViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MainListViewHolder, position: Int) {
        viewHolder.itemView.setOnClickListener {
            onClickListener.onClick(items[position].number)
        }
        viewHolder.place.text = items[position].place
        viewHolder.rocketName.text = items[position].rocketName
        viewHolder.missionName.text = items[position].name
        viewHolder.date.text = items[position].date.toDateString()
        viewHolder.number.text = String.format(
            viewHolder.itemView.context.getString(R.string.number),
            items[position].number
        )
        if (items[position].picture.isNotEmpty()) {
            Glide.with(viewHolder.itemView)
                .load(items[position].picture)
                .placeholder(R.drawable.sky)
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
        viewHolder.success.text = when (items[position].success) {
            true -> {
                val green = viewHolder.itemView.context.resources.getColor(R.color.success_green)
                viewHolder.success.setTextColor(green)
                val drawable: Drawable? = ContextCompat.getDrawable(viewHolder.itemView.context,R.drawable.ic_check)
                drawable?.colorFilter =
                    PorterDuffColorFilter(green, PorterDuff.Mode.SRC_IN)
                viewHolder.successImage.setImageDrawable(
                    drawable
                )
                viewHolder.itemView.context.getString(R.string.success)
            }
            else -> {
                val red = viewHolder.itemView.context.resources.getColor(R.color.failed_red)
                val drawable: Drawable? = ContextCompat.getDrawable(viewHolder.itemView.context,R.drawable.ic_report)
                drawable?.colorFilter =
                    PorterDuffColorFilter(red, PorterDuff.Mode.SRC_IN)
                viewHolder.successImage.setImageDrawable(
                    drawable
                )
                viewHolder.success.setTextColor(red)
                viewHolder.itemView.context.getString(R.string.failed)
            }
        }
    }


    class OnClickListener(val clickListener: (id: String) -> Unit) {
        fun onClick(id: String) = clickListener(id)
    }

    fun setItems(strings: List<LaunchUiModel>) {
        items = strings
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

}



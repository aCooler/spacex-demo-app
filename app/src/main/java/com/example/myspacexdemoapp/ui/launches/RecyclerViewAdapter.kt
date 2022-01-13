package com.example.myspacexdemoapp.ui.launches

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.LaunchData
import com.example.domain.LinkInfo
import com.example.domain.Mission
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.databinding.MainListCardBinding
import com.example.myspacexdemoapp.setColor
import com.example.myspacexdemoapp.toDateString

class RecyclerViewAdapter(private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<RecyclerViewAdapter.MainListViewHolder>() {
    private var items: List<LaunchData> = emptyList()

    class MainListViewHolder(binding: MainListCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val place: TextView = binding.place
        val rocketName: TextView = binding.rocketName
        val success: TextView = binding.success
        val successImage: ImageView = binding.successIcon
        val missionName: TextView = binding.missionName
        val date: TextView = binding.date
        val number: TextView = binding.number
        val picture: ImageView = binding.launchImage
        val badge: ImageView = binding.badge
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MainListViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.main_list_card, viewGroup, false)

        return MainListViewHolder(MainListCardBinding.bind(view))
    }

    override fun onBindViewHolder(viewHolder: MainListViewHolder, position: Int) {
        viewHolder.itemView.setOnClickListener {
            onClickListener.onClick(items[position].number, items[position].mission.name)
        }
        if (items[position].mission != Mission.EMPTY) {
            viewHolder.place.text = items[position].mission.place
            viewHolder.rocketName.text = items[position].mission.rocketName
            viewHolder.missionName.text = items[position].mission.name
            viewHolder.date.text = items[position].mission.date.toDateString()
            viewHolder.success.text = makeSuccess(viewHolder, position)
        }
        if (items[position].number != LaunchData.EMPTY.number) {
            viewHolder.number.text = String.format(
                viewHolder.itemView.context.getString(R.string.number),
                items[position].number
            )
        }
        if (items[position].linkInfo != LinkInfo.EMPTY) {
            Glide.with(viewHolder.itemView)
                .load(items[position].linkInfo.picture)
                .centerCrop()
                .placeholder(R.drawable.sky)
                .into(viewHolder.picture)
            Glide.with(viewHolder.itemView)
                .load(items[position].linkInfo.badge)
                .into(viewHolder.badge)
            viewHolder.badge.isVisible = items[position].linkInfo.badge.isNotEmpty()
        }
    }

    private fun makeSuccess(viewHolder: MainListViewHolder, position: Int): CharSequence {
        return when (items[position].mission.success) {
            true -> {
                val green =
                    ContextCompat.getColor(viewHolder.itemView.context, R.color.success_green)
                viewHolder.success.setTextColor(green)
                val drawable: Drawable? =
                    ContextCompat.getDrawable(viewHolder.itemView.context, R.drawable.ic_check)
                drawable?.setColor(green)
                viewHolder.successImage.setImageDrawable(
                    drawable
                )
                viewHolder.itemView.context.getString(R.string.success)
            }
            else -> {
                val red = ContextCompat.getColor(viewHolder.itemView.context, R.color.failed_red)
                val drawable: Drawable? =
                    ContextCompat.getDrawable(viewHolder.itemView.context, R.drawable.ic_report)
                drawable?.setColor(red)
                viewHolder.successImage.setImageDrawable(
                    drawable
                )
                viewHolder.success.setTextColor(red)
                viewHolder.itemView.context.getString(R.string.failed)
            }
        }
    }

    class OnClickListener(val clickListener: (id: String?, payloadId: String?) -> Unit) {
        fun onClick(id: String?, payloadId: String?) = clickListener(id, payloadId)
    }

    fun setItems(strings: List<LaunchData>) {
        items = strings
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size
}

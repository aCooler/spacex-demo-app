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
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.databinding.MainListCardBinding
import com.example.myspacexdemoapp.setColor
import com.example.myspacexdemoapp.ui.MainScreenModel

class RecyclerViewAdapter(private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<RecyclerViewAdapter.MainListViewHolder>() {
    private var items: List<MainScreenModel> = emptyList()

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
            onClickListener.onClick(items[position].number)
        }
        if (items[position].launch.isNotEmpty()) {
            viewHolder.place.text = items[position].place
            viewHolder.rocketName.text = items[position].rocket
            viewHolder.missionName.text = items[position].launch
            viewHolder.date.text = items[position].date
            viewHolder.success.text = makeSuccess(viewHolder, position)
        }
        if (items[position].number.isNotEmpty()) {
            viewHolder.number.text = String.format(
                viewHolder.itemView.context.getString(R.string.number),
                items[position].number
            )
        }
        if (items[position].pictureUrl.isNotEmpty()) {
            Glide.with(viewHolder.itemView)
                .load(items[position].pictureUrl)
                .centerCrop()
                .placeholder(R.drawable.sky)
                .into(viewHolder.picture)
            Glide.with(viewHolder.itemView)
                .load(items[position].badgeUrl)
                .into(viewHolder.badge)
            viewHolder.badge.isVisible = items[position].badgeUrl.isNotEmpty()
        }
    }

    private fun makeSuccess(viewHolder: MainListViewHolder, position: Int): CharSequence {
        return when (items[position].success) {
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

    class OnClickListener(val clickListener: (id: String?) -> Unit) {
        fun onClick(id: String?) = clickListener(id)
    }

    fun setItems(strings: List<MainScreenModel>) {
        items = strings
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size
}

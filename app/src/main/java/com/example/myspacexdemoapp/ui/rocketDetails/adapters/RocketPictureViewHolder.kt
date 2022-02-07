package com.example.myspacexdemoapp.ui.launch.adapters

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.databinding.RocketDetailsPictureBinding
import com.example.myspacexdemoapp.setColor
import com.example.myspacexdemoapp.ui.rocketDetails.RocketDetailsUIModel

class RocketPictureViewHolder(binding: RocketDetailsPictureBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val launchImage: ImageView = binding.launchImage
    private val active: TextView = binding.active

    fun onBindView(model: RocketDetailsUIModel.MainInfo) {
        if (model.pictureUrl.isNotEmpty()) {
            Glide.with(itemView)
                .load(model.pictureUrl)
                .centerCrop()
                .placeholder(R.drawable.sky)
                .into(
                    launchImage
                )
        }
        makeSuccess(model)
    }

    private fun makeSuccess(model: RocketDetailsUIModel.MainInfo) {
        when (model.success) {
            true -> {
                val green = ContextCompat.getColor(itemView.context, R.color.success_green)
                active.setTextColor(green)
                val drawable: Drawable? =
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_check)
                drawable?.setColor(green)
                active.text = itemView.context.getString(R.string.active)
            }
            else -> {
                val red = ContextCompat.getColor(itemView.context, R.color.failed_red)
                val drawable: Drawable? =
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_report)
                drawable?.setColor(red)
                active.setTextColor(red)
                active.text = itemView.context.getString(R.string.inactive)
            }
        }
    }
}

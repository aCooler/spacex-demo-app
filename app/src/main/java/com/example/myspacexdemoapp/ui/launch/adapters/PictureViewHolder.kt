package com.example.myspacexdemoapp.ui.launch.adapters

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.databinding.PictureBinding
import com.example.myspacexdemoapp.setColor
import com.example.myspacexdemoapp.ui.UIModel

class PictureViewHolder(binding: PictureBinding) : RecyclerView.ViewHolder(binding.root) {
    private val success: TextView = binding.success
    private val number: TextView = binding.number
    private val picture: ImageView = binding.launchImage
    private val successIcon: ImageView = binding.successIcon
    private val badge: ImageView = binding.badge

    fun onBindView(model: UIModel.MainInfo) {
        itemView.setOnClickListener {}
        number.text = String.format(
            itemView.context.getString(R.string.number),
            model.number
        )

        if (model.pictureUrl.isNotEmpty()) {
            Glide.with(itemView)
                .load(model.pictureUrl)
                .centerCrop()
                .placeholder(R.drawable.sky)
                .into(
                    picture
                )
        }
        if (model.badgeUrl.isNotEmpty()) {
            Glide.with(itemView)
                .load(model.badgeUrl)
                .into(
                    badge
                )
            badge.visibility = View.VISIBLE
        }

        success.text = makeSuccess(model)
    }

    private fun makeSuccess(model: UIModel.MainInfo): CharSequence {
        return when (model.success) {
            true -> {
                val green = ContextCompat.getColor(itemView.context, R.color.success_green)
                success.setTextColor(green)
                val drawable: Drawable? =
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_check)
                drawable?.setColor(green)
                successIcon.setImageDrawable(
                    drawable
                )
                itemView.context.getString(R.string.success)
            }
            else -> {
                val red = ContextCompat.getColor(itemView.context, R.color.failed_red)
                val drawable: Drawable? =
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_report)
                drawable?.setColor(red)
                successIcon.setImageDrawable(
                    drawable
                )
                success.setTextColor(red)
                itemView.context.getString(R.string.failed)
            }
        }
    }
}

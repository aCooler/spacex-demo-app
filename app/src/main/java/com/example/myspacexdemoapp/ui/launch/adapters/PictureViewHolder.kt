package com.example.myspacexdemoapp.ui.launch.adapters

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.ui.DataModel

class PictureViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val success: TextView = view.findViewById(R.id.success)
    private val number: TextView = view.findViewById(R.id.number)
    private val picture: ImageView = view.findViewById(R.id.launch_image)
    private val successIcon: ImageView = view.findViewById(R.id.success_icon)
    private val badge: ImageView = view.findViewById(R.id.badge)

    fun onBindView(model: DataModel.MainInfo) {
        itemView.setOnClickListener {}
        number.text = String.format(
            itemView.context.getString(R.string.number),
            model.number
        )

        if (model.pictureUrl.isNotEmpty()) {
            Glide.with(itemView)
                .load(model.pictureUrl)
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

    private fun makeSuccess(model: DataModel.MainInfo): CharSequence {
        return when (model.success) {
            true -> {
                val green = ContextCompat.getColor(itemView.context, R.color.success_green)
                success.setTextColor(green)
                val drawable: Drawable? =
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_check)
                drawable?.colorFilter =
                    PorterDuffColorFilter(green, PorterDuff.Mode.SRC_IN)
                successIcon.setImageDrawable(
                    drawable

                )
                itemView.context.getString(R.string.success)
            }
            else -> {
                val red = ContextCompat.getColor(itemView.context, R.color.failed_red)
                val drawable: Drawable? =
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_report)
                drawable?.colorFilter =
                    PorterDuffColorFilter(red, PorterDuff.Mode.SRC_IN)
                successIcon.setImageDrawable(
                    drawable
                )
                success.setTextColor(red)
                itemView.context.getString(R.string.failed)
            }
        }
    }
}

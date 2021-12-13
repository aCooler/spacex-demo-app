package com.example.myspacexdemoapp.ui.launch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myspacexdemoapp.R

class CategoryViewHolder constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val viewPagerItem: ImageView = itemView.findViewById(R.id.view_pager_image)

    constructor(parent: ViewGroup) : this(
        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_pager_item, parent, false)
    )

    fun bind(imageUrl: String) {
        Glide.with(itemView.context)
            .load(imageUrl)
            .error(R.drawable.sky)
            .centerCrop()
            .placeholder(R.drawable.sky)
            .into(viewPagerItem)
    }
}

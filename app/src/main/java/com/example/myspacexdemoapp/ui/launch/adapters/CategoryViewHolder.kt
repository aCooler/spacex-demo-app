package com.example.myspacexdemoapp.ui.launch.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.databinding.ViewPagerItemBinding

class CategoryViewHolder constructor(private val binding: ViewPagerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val viewPagerItem: ImageView = binding.viewPagerImage

    constructor(parent: ViewGroup) : this(
        ViewPagerItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
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

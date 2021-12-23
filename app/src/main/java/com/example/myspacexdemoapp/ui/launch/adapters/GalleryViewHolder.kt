package com.example.myspacexdemoapp.ui.launch.adapters

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myspacexdemoapp.databinding.GalleryBinding
import com.example.myspacexdemoapp.ui.DataModel

class GalleryViewHolder(private val binding: GalleryBinding) : RecyclerView.ViewHolder(binding.root) {
    private val pager: ViewPager2 = binding.viewPager
    private val adapter = CategoryAdapter()
    fun onBindView(model: DataModel.Gallery) {
        pager.adapter = adapter
        adapter.setItem(model.pictures)
    }
}

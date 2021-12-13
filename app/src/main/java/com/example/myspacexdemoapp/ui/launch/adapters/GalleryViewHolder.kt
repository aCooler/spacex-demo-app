package com.example.myspacexdemoapp.ui.launch.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.ui.DataModel

class GalleryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val pager: ViewPager2 = view.findViewById(R.id.viewPager)
    private val adapter = CategoryAdapter()
    fun onBindView(model: DataModel.Gallery) {
        pager.adapter = adapter
        adapter.setItem(model.pictures)
    }
}

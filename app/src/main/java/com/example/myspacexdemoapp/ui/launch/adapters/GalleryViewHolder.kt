package com.example.myspacexdemoapp.ui.launch.adapters

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myspacexdemoapp.databinding.GalleryBinding
import com.example.myspacexdemoapp.ui.UIModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class GalleryViewHolder(binding: GalleryBinding) : RecyclerView.ViewHolder(binding.root) {
class GalleryViewHolder(binding: GalleryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val pager: ViewPager2 = binding.viewPager
    private val tabLayout: TabLayout = binding.tabDots
    private val adapter = CategoryAdapter()
    fun onBindView(model: UIModel.Gallery) {
        pager.adapter = adapter
        TabLayoutMediator(tabLayout, pager) { tab, position -> }.attach()
        adapter.setItem(model.pictures)
    }
}

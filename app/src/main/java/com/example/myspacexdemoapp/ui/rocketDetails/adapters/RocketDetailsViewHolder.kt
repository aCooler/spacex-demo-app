package com.example.myspacexdemoapp.ui.launch.adapters

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.databinding.DetailsBinding
import com.example.myspacexdemoapp.ui.rocketDetails.RocketDetailsUIModel

class RocketDetailsViewHolder(binding: DetailsBinding) : RecyclerView.ViewHolder(binding.root) {
    val details: TextView = binding.details
    fun onBindView(model: RocketDetailsUIModel.Details) {
        details.text = model.details
        (details.layoutParams as RecyclerView.LayoutParams).apply {
            topMargin = 20
            bottomMargin = 40
        }
    }
}

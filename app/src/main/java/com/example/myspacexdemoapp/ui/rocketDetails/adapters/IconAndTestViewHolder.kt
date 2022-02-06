package com.example.myspacexdemoapp.ui.rocketDetails.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.databinding.IconAndTextBinding
import com.example.myspacexdemoapp.ui.rocketDetails.RocketDetailsUIModel

class IconAndTestViewHolder(binding: IconAndTextBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val icon: ImageView = binding.iconAndTextIcon
    private val text: TextView = binding.iconAndTextText

    fun onBindView(model: RocketDetailsUIModel.IconAndText) {
        icon.setImageResource(model.icon)
        text.text= model.text
    }
}

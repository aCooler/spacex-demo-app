package com.example.myspacexdemoapp.ui.launch.adapters

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.databinding.DetailsBinding
import com.example.myspacexdemoapp.ui.DataModel

class DetailsViewHolder(private val binding: DetailsBinding) : RecyclerView.ViewHolder(binding.root) {
    val details: TextView = binding.details
    fun onBindView(model: DataModel.Details) {
        details.text = model.details
    }
}

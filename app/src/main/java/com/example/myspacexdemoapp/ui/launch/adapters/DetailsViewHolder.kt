package com.example.myspacexdemoapp.ui.launch.adapters

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.databinding.DetailsBinding
import com.example.myspacexdemoapp.ui.UIModel

class DetailsViewHolder(binding: DetailsBinding) : RecyclerView.ViewHolder(binding.root) {
    val details: TextView = binding.details
    fun onBindView(model: UIModel.Details) {
        details.text = model.details
    }
}

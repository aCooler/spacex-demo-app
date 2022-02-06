package com.example.myspacexdemoapp.ui.launch.adapters

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.databinding.RowRocketsDetailsBinding
import com.example.myspacexdemoapp.ui.rocketDetails.RocketDetailsUIModel

class RocketRowViewHolder(binding: RowRocketsDetailsBinding) : RecyclerView.ViewHolder(binding.root) {
    private val row: TextView = binding.row
    fun onBindView(model: RocketDetailsUIModel.Row) {
        row.text = model.word
    }
}

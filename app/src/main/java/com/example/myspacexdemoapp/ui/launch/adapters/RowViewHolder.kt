package com.example.myspacexdemoapp.ui.launch.adapters

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.databinding.RowBinding
import com.example.myspacexdemoapp.ui.DataModel

class RowViewHolder(private val binding: RowBinding) : RecyclerView.ViewHolder(binding.root) {
    private val row: TextView = binding.row
    fun onBindView(model: DataModel.SingleString) {
        row.text = model.word
    }
}

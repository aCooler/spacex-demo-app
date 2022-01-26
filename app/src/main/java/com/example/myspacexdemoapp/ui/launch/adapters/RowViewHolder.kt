package com.example.myspacexdemoapp.ui.launch.adapters

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.databinding.RowBinding
import com.example.myspacexdemoapp.ui.UIModel

class RowViewHolder(binding: RowBinding) : RecyclerView.ViewHolder(binding.root) {
    private val row: TextView = binding.row
    fun onBindView(model: UIModel.SingleString) {
        row.text = model.word
    }
}

package com.example.myspacexdemoapp.ui.launch.adapters

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.databinding.SingleBinding
import com.example.myspacexdemoapp.ui.UIModel

class SingleViewHolder(binding: SingleBinding) : RecyclerView.ViewHolder(binding.root) {
    private val singleTitle: TextView = binding.singleTitle
    private val singleText: TextView = binding.singleText

    fun onBindView(model: UIModel.TitleAndText) {
        singleTitle.text = model.title
        singleText.text = model.text
    }
}

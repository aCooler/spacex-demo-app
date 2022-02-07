package com.example.myspacexdemoapp.ui.rocketDetails.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.databinding.RowExpandableBinding
import com.example.myspacexdemoapp.ui.rocketDetails.RocketDetailsUIModel

class ExpandableRowViewHolder(binding: RowExpandableBinding, private val expandItem: (Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {
    val text = binding.expandableText
    val layout = binding.root

    fun onBindView(model: RocketDetailsUIModel.RowExpandable) {
        text.text = model.word
        layout.setOnClickListener {
            expandItem(model.position)
        }
    }
}

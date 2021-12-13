package com.example.myspacexdemoapp.ui.launch.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.ui.DataModel

class RowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val row: TextView = view.findViewById(R.id.row)
    fun onBindView(model: DataModel.SingleString) {
        row.text = model.word
    }
}
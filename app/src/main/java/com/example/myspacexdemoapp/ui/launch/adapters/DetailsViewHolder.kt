package com.example.myspacexdemoapp.ui.launch.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.ui.DataModel

class DetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val details: TextView = view.findViewById(R.id.details)
    fun onBindView(model: DataModel.Details) {
        details.text = model.details
    }
}

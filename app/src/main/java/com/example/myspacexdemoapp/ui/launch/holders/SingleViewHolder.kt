package com.example.myspacexdemoapp.ui.launch.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.ui.DataModel

class SingleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val singleTitle: TextView = view.findViewById(R.id.single_title)
    private val singleText: TextView = view.findViewById(R.id.single_text)

    fun onBindView(model: DataModel.TitleAndText) {
        singleTitle.text = model.title
        singleText.text = model.text
    }
}

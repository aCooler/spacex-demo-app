package com.example.myspacexdemoapp.ui.launch.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.api.toDateString
import com.example.myspacexdemoapp.ui.DataModel

class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val place: TextView = view.findViewById(R.id.place)
    private val rocketName: TextView = view.findViewById(R.id.rocket_name)
    private val date: TextView = view.findViewById(R.id.date)
    private val reused: TextView = view.findViewById(R.id.reused)

    fun onBindView(model: DataModel.LaunchEvent) {
        place.text = model.place
        rocketName.text = model.rocket
        date.text = model.date?.toDateString()
        val reusedCheck = model.reused
        if (reusedCheck != null) {
            reused.text = if (reusedCheck) {
                itemView.context.getString(R.string.reused)
            } else {
                itemView.context.getString(R.string.notreused)
            }
        }
    }
}
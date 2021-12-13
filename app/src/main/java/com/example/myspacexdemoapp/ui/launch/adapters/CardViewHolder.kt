package com.example.myspacexdemoapp.ui.launch.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.toDateString
import com.example.myspacexdemoapp.ui.DataModel

class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val place: TextView = view.findViewById(R.id.place)
    private val rocketName: TextView = view.findViewById(R.id.rocket_name)
    private val date: TextView = view.findViewById(R.id.date)
    private val reused: TextView = view.findViewById(R.id.reused)
    private val reusedIcon: ImageView = view.findViewById(R.id.reused_icon)
    private val rocketNameIcon: ImageView = view.findViewById(R.id.rocket_name_icon)
    private val dateIcon: ImageView = view.findViewById(R.id.date_icon)
    private val placeIcon: ImageView = view.findViewById(R.id.place_icon)

    fun onBindView(model: DataModel.LaunchEvent) {
        if (model.date.isEmpty()) {
            date.visibility = View.GONE
            dateIcon.visibility = View.GONE
        } else {
            date.text = model.date.toDateString()
        }
        if (model.place.isEmpty()) {
            place.visibility = View.GONE
            placeIcon.visibility = View.GONE
        } else {
            place.text = model.place
        }
        if (model.rocket.isEmpty()) {
            rocketName.visibility = View.GONE
            rocketNameIcon.visibility = View.GONE
        } else {
            rocketName.text = model.rocket
        }
        val reusedCheck = model.reused
        reused.text = if (reusedCheck) {
            itemView.context.getString(R.string.reused)
        } else {
            itemView.context.getString(R.string.notreused)
        }
    }
}

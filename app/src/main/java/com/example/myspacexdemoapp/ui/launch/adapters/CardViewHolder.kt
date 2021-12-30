package com.example.myspacexdemoapp.ui.launch.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.databinding.MissionCardBinding
import com.example.myspacexdemoapp.toDateString
import com.example.myspacexdemoapp.ui.UIModel

class CardViewHolder(binding: MissionCardBinding) : RecyclerView.ViewHolder(binding.root) {
    private val place: TextView = binding.place
    private val rocketName: TextView = binding.rocketName
    private val date: TextView = binding.date
    private val reused: TextView = binding.reused
    private val reusedIcon: ImageView = binding.reusedIcon
    private val rocketNameIcon: ImageView = binding.rocketNameIcon
    private val dateIcon: ImageView = binding.dateIcon
    private val placeIcon: ImageView = binding.placeIcon

    fun onBindView(model: UIModel.LaunchEvent) {
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

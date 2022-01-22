package com.example.myspacexdemoapp.ui.start

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.databinding.TimerCardBinding

class TimerViewHolder(binding: TimerCardBinding) : RecyclerView.ViewHolder(binding.root) {
/*    private val place: TextView = binding.place
    private val rocketName: TextView = binding.rocketName
    private val date: TextView = binding.date
    private val reused: TextView = binding.reused
    private val reusedIcon: ImageView = binding.reusedIcon
    private val rocketNameIcon: ImageView = binding.rocketNameIcon
    private val dateIcon: ImageView = binding.dateIcon
    private val placeIcon: ImageView = binding.placeIcon*/



    private val name: TextView = binding.name
    private val days: TextView = binding.days
    private val hours: TextView = binding.hours
    private val minutes: TextView = binding.minutes
    private val seconds: TextView = binding.seconds


    fun onBindView(model: StartUIModel.Timer) {

        if (model.name.isEmpty()) {
            name.visibility = View.GONE
        } else {
            name.text = model.name
        }

        if (model.days.isEmpty()) {
            days.visibility = View.GONE
        } else {
            days.text = model.days
        }
        if (model.hours.isEmpty()) {
            hours.visibility = View.GONE
        } else {
            hours.text = model.hours
        }
        if (model.minutes.isEmpty()) {
            minutes.visibility= View.GONE
        } else {
            minutes.text = model.minutes
        }
        if (model.seconds.isEmpty()) {
            seconds.visibility= View.GONE
        } else {
            seconds.text = model.seconds
        }
    }
}


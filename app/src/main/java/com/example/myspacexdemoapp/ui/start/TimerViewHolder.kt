package com.example.myspacexdemoapp.ui.start

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.databinding.TimerCardBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TimerViewHolder(
    binding: TimerCardBinding,
    private val onClickListener: StartAdapter.OnClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    private val name: TextView = binding.timerCardLaunchName
    private val days: TextView = binding.timerCardDaysValue
    private val hours: TextView = binding.timerCardHoursValue
    private val minutes: TextView = binding.timerCardMinutesValue
    private val seconds: TextView = binding.timerCardSecondsValue
    private val timerButton: FloatingActionButton = binding.timerCardTimerButton

    fun onBindView(model: StartUIModel.Timer) {
        timerButton.setOnClickListener {
            onClickListener.onClick()
        }
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
            minutes.visibility = View.GONE
        } else {
            minutes.text = model.minutes
        }
        if (model.seconds.isEmpty()) {
            seconds.visibility = View.GONE
        } else {
            seconds.text = model.seconds
        }
    }
}

package com.example.myspacexdemoapp.ui.start

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.databinding.TimerCardBinding
import com.example.myspacexdemoapp.textValue
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TimerViewHolder(
    binding: TimerCardBinding,
    private val onClickListener: HomeAdapter.OnClickListener,
    private val onModel: HomeAdapter.OnModelChanged,
) : RecyclerView.ViewHolder(binding.root) {
    private val name: TextView = binding.timerCardLaunchName
    private val days: TextView = binding.timerCardDaysValue
    private val hours: TextView = binding.timerCardHoursValue
    private val minutes: TextView = binding.timerCardMinutesValue
    private val seconds: TextView = binding.timerCardSecondsValue
    private val timerButton: FloatingActionButton = binding.timerCardTimerButton

    init {
        this.onModel.invoke = { model -> onTimerChanged(model) }
    }

    private fun onTimerChanged(model: StartUIModel.Timer) {
        name.textValue = model.name
        days.textValue = model.days
        hours.textValue = model.hours
        minutes.textValue = model.minutes
        seconds.textValue = model.seconds
    }

    fun onBindView(model: StartUIModel.Timer) {
        timerButton.setOnClickListener {
            onClickListener.onClick()
        }
        onTimerChanged(model)
    }
}

package com.example.myspacexdemoapp.ui.start

import android.content.Context
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.databinding.LaunchesTotalBinding

class LaunchesViewHolder(binding: LaunchesTotalBinding) : RecyclerView.ViewHolder(binding.root) {
    private val successful: TextView = binding.successful
    private val total: TextView = binding.total
    private val efficiency: TextView = binding.efficiency
    private val toLaunch: TextView = binding.toLaunch
    private val card: ConstraintLayout = binding.root

    fun onBindView(model: StartUIModel.Launches) {
        val wm: WindowManager = card.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display = wm.defaultDisplay
        card.layoutParams.height = display.height / 2
        if (model.successful.isEmpty()) {
            successful.visibility = View.GONE
        } else {
            successful.text = model.successful
        }
        if (model.total.isEmpty()) {
            total.visibility = View.GONE
        } else {
            total.text = model.total
        }
        val efficiencyNumber = (model.successful.toFloat() / model.total.toFloat() * 100).toInt()
        val eff = "Revolutionizing space flight with an efficiency of $efficiencyNumber% since 2006"
        if (model.efficiency.isEmpty()) {
            efficiency.visibility = View.GONE
        } else {
            efficiency.text = eff
        }
        if (model.efficiency.isEmpty()) {
            efficiency.visibility = View.GONE
        } else {
            efficiency.text = eff
        }
        toLaunch.setOnClickListener {
            findNavController(it).navigate(
                R.id.action_myStartFragment_to_myMainFragment,
                null,
                null
            )
        }
    }
}


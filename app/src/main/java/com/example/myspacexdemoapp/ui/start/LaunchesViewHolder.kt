package com.example.myspacexdemoapp.ui.start

import android.content.Context
import android.view.Display
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.databinding.LaunchesTotalBinding
import com.example.myspacexdemoapp.getTopHeight
import com.example.myspacexdemoapp.textValue

class LaunchesViewHolder(binding: LaunchesTotalBinding) : RecyclerView.ViewHolder(binding.root) {
    private val successful: TextView = binding.successfulLaunchesValue
    private val total: TextView = binding.totalLaunchesValue
    private val efficiency: TextView = binding.efficiency
    private val toLaunch: Button = binding.button
    private val card: ConstraintLayout = binding.root

    fun onBindView(model: StartUIModel.Launches) {
        val wm: WindowManager =
            card.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display = wm.defaultDisplay
        card.layoutParams.height = (display.height - getTopHeight(card = card)) / 2
        successful.textValue = model.successful
        total.textValue = model.total
        val efficiencyNumber = (model.successful.toFloat() / model.total.toFloat() * 100).toInt()
        efficiency.textValue =
            "Revolutionizing space flight with an efficiency of $efficiencyNumber% since 2006"
        toLaunch.setOnClickListener {
            findNavController(it).navigate(
                R.id.action_myStartFragment_to_myMainFragment,
                null,
                null
            )
        }
    }
}

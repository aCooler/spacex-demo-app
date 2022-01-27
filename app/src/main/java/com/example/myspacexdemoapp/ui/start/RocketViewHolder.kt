package com.example.myspacexdemoapp.ui.start

import android.content.Context
import android.view.Display
import android.view.WindowManager
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.databinding.RocketsStartBinding

class RocketViewHolder(binding: RocketsStartBinding) : RecyclerView.ViewHolder(binding.root) {
    private val toLaunch: TextView = binding.toRocket
    private val card: ConstraintLayout = binding.root

    fun onBindView(model: StartUIModel.Rockets) {
        val wm: WindowManager = card.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display = wm.defaultDisplay
        card.layoutParams.height = display.height / 2
        toLaunch.setOnClickListener {
            findNavController(it).navigate(
                R.id.action_myStartFragment_to_myMainFragment,
                null,
                null
            )
        }
    }
}

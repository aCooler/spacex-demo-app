package com.example.myspacexdemoapp.ui.start

import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.databinding.RocketsStartBinding

class RocketViewHolder(binding: RocketsStartBinding) : RecyclerView.ViewHolder(binding.root) {
    private val toLaunch: TextView = binding.toRocket

    fun onBindView(model: StartUIModel.Rockets) {
        toLaunch.setOnClickListener {
            findNavController(it).navigate(
                R.id.action_myStartFragment_to_myMainFragment,
                null,
                null
            )
        }
    }


}

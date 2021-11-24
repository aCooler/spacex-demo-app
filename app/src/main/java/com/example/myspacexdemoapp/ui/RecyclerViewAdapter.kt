package com.example.myspacexdemoapp.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.R

class RecyclerViewAdapter :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private var items: List<LaunchUiModel> = listOf()


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rocketName: TextView
        val success: TextView
        val place: TextView
        val missionName: TextView
        val date: TextView
        val picture: ImageView
        val badge: ImageView

        init {
            rocketName = view.findViewById(R.id.rocketName)
            place = view.findViewById(R.id.place)
            missionName = view.findViewById(R.id.missionName)
            date = view.findViewById(R.id.date)
            success = view.findViewById(R.id.success)
            picture = view.findViewById(R.id.picture)
            badge = view.findViewById(R.id.badge)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.launch_card, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.rocketName.text = items[position].rocketName
        viewHolder.place.text = items[position].place
        viewHolder.missionName.text = items[position].name
        viewHolder.date.text = items[position].date
        viewHolder.success.text = when (items[position].success) {
            true -> {
                viewHolder.success.highlightColor = Color.GREEN
                "V success"}
            else -> {
                viewHolder.success.highlightColor = Color.RED
                "x failed"}
        }

    }


    fun setItems(strings: List<LaunchUiModel>) {
        items = strings
        notifyDataSetChanged()

    }

    override fun getItemCount() = items.size

}
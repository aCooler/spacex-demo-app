package com.example.myspacexdemoapp.ui.rockets

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.RocketData
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.databinding.RocketListCardBinding

class RocketsAdapter(private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<RocketsAdapter.RocketsViewHolder>() {
    private var items: List<RocketData> = emptyList()

    class RocketsViewHolder(binding: RocketListCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val picture: ImageView = binding.launchImage
        val rocket: TextView = binding.rocket
        val firstFlight: TextView = binding.date
        val country: TextView = binding.rocketName
        val cost: TextView = binding.place
        val stages: TextView = binding.reused
        val active: TextView = binding.active
        val root: CardView = binding.root
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RocketsViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.rocket_list_card, viewGroup, false)

        return RocketsViewHolder(RocketListCardBinding.bind(view))
    }

    override fun onBindViewHolder(viewHolder: RocketsViewHolder, position: Int) {
        viewHolder.cost.text = "${items[position].cost} Per Launch"
        viewHolder.country.text = items[position].country
        viewHolder.firstFlight.text = "First Flight ${items[position].firstFlight}"
        viewHolder.stages.text =  "${viewHolder.stages.text} Stages"
        viewHolder.rocket.text = items[position].name
        viewHolder.root.setOnClickListener {
            onClickListener.onClick(items[position].name)
        }
        if (items[position].rocketPicture != RocketData.EMPTY.rocketPicture) {
            Glide.with(viewHolder.itemView)
                .load(items[position].rocketPicture)
                .centerCrop()
                .placeholder(R.drawable.sky)
                .into(viewHolder.picture)
        }
        viewHolder.active.text = makeSuccess(viewHolder, position)
    }

    private fun makeSuccess(viewHolder: RocketsViewHolder, position: Int): CharSequence {
         return when (items[position].activity) {
            true -> {
                val green =
                    ContextCompat.getColor(viewHolder.itemView.context, R.color.success_green)
                viewHolder.active.setTextColor(green)
                ContextCompat.getDrawable(viewHolder.itemView.context, R.drawable.ic_check)
                viewHolder.itemView.context.getString(R.string.active)
            }
            else -> {
                val red = ContextCompat.getColor(viewHolder.itemView.context, R.color.failed_red)
                viewHolder.active.setTextColor(red)
                viewHolder.itemView.context.getString(R.string.inactive)
            }
        }
    }

    class OnClickListener(val clickListener: (id: String?) -> Unit) {
        fun onClick(id: String?) = clickListener(id)
    }

    fun setItems(strings: List<RocketData>) {
        items = strings
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size
}

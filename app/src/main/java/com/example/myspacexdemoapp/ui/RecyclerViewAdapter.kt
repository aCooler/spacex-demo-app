package com.example.myspacexdemoapp.ui

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myspacexdemoapp.R
import java.text.SimpleDateFormat


class RecyclerViewAdapter :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private var items: List<LaunchUiModel> = listOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val place: TextView
        val rocketName: TextView
        val success: TextView
        val missionName: TextView
        val date: TextView
        val number: TextView
        val picture: ImageView
        val badge: ImageView

        init {
            place = view.findViewById(R.id.place)
            rocketName = view.findViewById(R.id.rocket_name)
            missionName = view.findViewById(R.id.mission_name)
            date = view.findViewById(R.id.date)
            success = view.findViewById(R.id.success)
            number = view.findViewById(R.id.number)
            picture = view.findViewById(R.id.launch_image)
            badge = view.findViewById(R.id.badge)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.launch_card, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.place.text = items[position].place
        viewHolder.rocketName.text = items[position].rocketName
        viewHolder.missionName.text = items[position].name
        viewHolder.date.text = items[position].date
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("MMM dd , yyyy HH:mm a")
        viewHolder.date.text = formatter.format(parser.parse(items[position].date))
        viewHolder.number.text = "№ ${items[position].number}"
        val textView: TextView = viewHolder.success

        if (items[position].picture.isNotEmpty()) {
            Glide.with(viewHolder.itemView)
                .load(items[position].picture)
                .into(
                    viewHolder.picture
                )
        }


        if (items[position].badge.isNotEmpty()) {
            Glide.with(viewHolder.itemView)
                .load(items[position].badge)
                .into(
                    viewHolder.badge
                )
            viewHolder.badge.visibility = View.VISIBLE
        }



        viewHolder.success.text = when (items[position].success) {
            true -> {

                val green = viewHolder.itemView.context.resources.getColor(R.color.success_green)
                viewHolder.success.setTextColor(green)
                viewHolder.success.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_check,
                    0,
                    0,
                    0
                )
                viewHolder.success.compoundDrawables[0].colorFilter =
                    PorterDuffColorFilter(green, PorterDuff.Mode.SRC_IN)
                viewHolder.itemView.context.getString(R.string.success)
            }


            else -> {
                val red = viewHolder.itemView.context.resources.getColor(R.color.failed_red)
                viewHolder.success.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_report,
                    0,
                    0,
                    0
                )
                viewHolder.success.compoundDrawables[0].colorFilter =
                    PorterDuffColorFilter(red, PorterDuff.Mode.SRC_IN)
                viewHolder.success.setTextColor(red)
                viewHolder.itemView.context.getString(R.string.failed)
            }
        }


    }


    fun setItems(strings: List<LaunchUiModel>) {
        items = strings
        notifyDataSetChanged()

    }

    override fun getItemCount() = items.size

}
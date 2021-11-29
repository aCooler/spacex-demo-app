package com.example.myspacexdemoapp.ui.launches

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.api.toDateString

class RecyclerViewAdapter(private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private var items: List<LaunchUiModel> = emptyList()


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
            missionName = view.findViewById(R.id.row)
            date = view.findViewById(R.id.single_title)
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

        viewHolder.itemView.setOnClickListener {
            onClickListener.onClick(items[position].number)
        }

        viewHolder.place.text = items[position].place
        viewHolder.rocketName.text = items[position].rocketName
        viewHolder.missionName.text = items[position].name
        viewHolder.date.text = items[position].date.toDateString()
        viewHolder.number.text = String.format(
            viewHolder.itemView.context.getString(R.string.number),
            items[position].number
        )

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

        val drawable: Drawable?
        drawable =
            viewHolder.itemView.context!!.resources.getDrawable(R.drawable.ic_check,viewHolder.itemView.context!!.theme)

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
//                viewHolder.success.compoundDrawables[0].setBounds(0, 0, 0 + viewHolder.success.compoundDrawables[0].intrinsicWidth, 0
//                        + viewHolder.success.compoundDrawables[0].intrinsicHeight);


                viewHolder.success.setTextColor(red)
                viewHolder.itemView.context.getString(R.string.failed)
            }
        }






    }

    class OnClickListener(val clickListener: (id: String) -> Unit) {
        fun onClick(id: String) = clickListener(id)
    }
    fun setItems(strings: List<LaunchUiModel>) {
        items = strings
        notifyDataSetChanged()

    }

    override fun getItemCount() = items.size

}



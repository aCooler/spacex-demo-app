//package com.example.myspacexdemoapp.ui.launch
//
//import android.graphics.PorterDuff
//import android.graphics.PorterDuffColorFilter
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.myspacexdemoapp.R
//import com.example.myspacexdemoapp.api.toDateString
//import com.example.myspacexdemoapp.ui.launches.LaunchUiModel
//
//class DetailsRecyclerViewAdapter :
//    RecyclerView.Adapter<DetailsRecyclerViewAdapter.ViewHolder>() {
//    private var item: LaunchUiModel = LaunchUiModel()
//
//
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val place: TextView
//        val rocketName: TextView
//        val success: TextView
//        val missionName: TextView
//        val date: TextView
//        val number: TextView
//        val picture: ImageView
//        val badge: ImageView
//
//        init {
//            place = view.findViewById(R.id.place)
//            rocketName = view.findViewById(R.id.rocket_name)
//            missionName = view.findViewById(R.id.single_title)
//            date = view.findViewById(R.id.date)
//            success = view.findViewById(R.id.success)
//            number = view.findViewById(R.id.number)
//            picture = view.findViewById(R.id.launch_image)
//            badge = view.findViewById(R.id.badge)
//        }
//    }
//
//    class ViewHolder1(view: View) : RecyclerView.ViewHolder(view) {
//        val success: TextView
//        val number: TextView
//        val picture: ImageView
//        val badge: ImageView
//
//        init {
//            success = view.findViewById(R.id.success)
//            number = view.findViewById(R.id.number)
//            picture = view.findViewById(R.id.launch_image)
//            badge = view.findViewById(R.id.badge)
//        }
//    }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
//        var view = LayoutInflater.from(viewGroup.context)
//            .inflate(R.layout.card, viewGroup, false)
//
//        when(viewType){
//            0 -> {
//
//                var view = LayoutInflater.from(viewGroup.context)
//                    .inflate(R.layout.card, viewGroup, false)
//                return ViewHolder(view)
//
//            }
//            1-> {
//
//                var view = LayoutInflater.from(viewGroup.context)
//                    .inflate(R.layout.picture, viewGroup, false)
//                return ViewHolder1(view)
//
//            }
//        }
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        when(viewHolder.itemViewType){
//            0 -> {
//
//                viewHolder as ViewHolder
//                viewHolder.place.text = item.place
//                viewHolder.rocketName.text = item.rocketName
//                viewHolder.missionName.text = item.name
//                viewHolder.date.text = item.date.toDateString()
//                viewHolder.number.text = String.format(
//                    viewHolder.itemView.context.getString(R.string.number),
//                    item.number
//                )
//
//                if (item.picture.isNotEmpty()) {
//                    Glide.with(viewHolder.itemView)
//                        .load(item.picture)
//                        .into(
//                            viewHolder.picture
//                        )
//                }
//
//
//                if (item.badge.isNotEmpty()) {
//                    Glide.with(viewHolder.itemView)
//                        .load(item.badge)
//                        .into(
//                            viewHolder.badge
//                        )
//                    viewHolder.badge.visibility = View.VISIBLE
//                }
//
//                viewHolder.success.text = when (item.success) {
//                    true -> {
//
//                        val green = viewHolder.itemView.context.resources.getColor(R.color.success_green)
//                        viewHolder.success.setTextColor(green)
//                        viewHolder.success.setCompoundDrawablesWithIntrinsicBounds(
//                            R.drawable.ic_check,
//                            0,
//                            0,
//                            0
//                        )
//                        viewHolder.success.compoundDrawables[0].colorFilter =
//                            PorterDuffColorFilter(green, PorterDuff.Mode.SRC_IN)
//                        viewHolder.itemView.context.getString(R.string.success)
//                    }
//
//
//                    else -> {
//                        val red = viewHolder.itemView.context.resources.getColor(R.color.failed_red)
//                        viewHolder.success.setCompoundDrawablesWithIntrinsicBounds(
//                            R.drawable.ic_report,
//                            0,
//                            0,
//                            0
//                        )
//                        viewHolder.success.compoundDrawables[0].colorFilter =
//                            PorterDuffColorFilter(red, PorterDuff.Mode.SRC_IN)
//                        viewHolder.success.setTextColor(red)
//                        viewHolder.itemView.context.getString(R.string.failed)
//                    }
//                }
//
//
//
//
//            }
//            1 -> {
//
//                viewHolder as ViewHolder2
//                viewHolder.number.text = String.format(
//                    viewHolder.itemView.context.getString(R.string.number),
//                    item.number
//                )
//
//                if (item.picture.isNotEmpty()) {
//                    Glide.with(viewHolder.itemView)
//                        .load(item.picture)
//                        .into(
//                            viewHolder.picture
//                        )
//                }
//
//
//                if (item.badge.isNotEmpty()) {
//                    Glide.with(viewHolder.itemView)
//                        .load(item.badge)
//                        .into(
//                            viewHolder.badge
//                        )
//                    viewHolder.badge.visibility = View.VISIBLE
//                }
//
//                viewHolder.success.text = when (item.success) {
//                    true -> {
//
//                        val green = viewHolder.itemView.context.resources.getColor(R.color.success_green)
//                        viewHolder.success.setTextColor(green)
//                        viewHolder.success.setCompoundDrawablesWithIntrinsicBounds(
//                            R.drawable.ic_check,
//                            0,
//                            0,
//                            0
//                        )
//                        viewHolder.success.compoundDrawables[0].colorFilter =
//                            PorterDuffColorFilter(green, PorterDuff.Mode.SRC_IN)
//                        viewHolder.itemView.context.getString(R.string.success)
//                    }
//
//
//                    else -> {
//                        val red = viewHolder.itemView.context.resources.getColor(R.color.failed_red)
//                        viewHolder.success.setCompoundDrawablesWithIntrinsicBounds(
//                            R.drawable.ic_report,
//                            0,
//                            0,
//                            0
//                        )
//                        viewHolder.success.compoundDrawables[0].colorFilter =
//                            PorterDuffColorFilter(red, PorterDuff.Mode.SRC_IN)
//                        viewHolder.success.setTextColor(red)
//                        viewHolder.itemView.context.getString(R.string.failed)
//                    }
//                }
//
//
//
//
//            }
//        }
//
//
//
//
//    }
//
//    fun setItems(model: LaunchUiModel) {
//        item = model
//        notifyDataSetChanged()
//
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return position
//    }
//
//    override fun getItemCount(): Int {
//        return if (item.number.isEmpty()) {
//            0
//        } else {
//            2
//        }
//    }
//
//}
//
//

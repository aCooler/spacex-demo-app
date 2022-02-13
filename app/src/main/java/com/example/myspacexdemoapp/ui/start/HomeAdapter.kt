package com.example.myspacexdemoapp.ui.start

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.R
import com.example.myspacexdemoapp.databinding.LaunchesTotalBinding
import com.example.myspacexdemoapp.databinding.RocketsStartBinding
import com.example.myspacexdemoapp.databinding.TimerCardBinding

class HomeAdapter(private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfData: List<StartUIModel> = emptyList()
    private var onModel = OnModelChanged()
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TimerViewHolder -> holder.onBindView(listOfData[position] as StartUIModel.Timer)
            is LaunchesViewHolder -> holder.onBindView(listOfData[position] as StartUIModel.Launches)
            is RocketViewHolder -> holder.onBindView(listOfData[position] as StartUIModel.Rockets)
        }
    }

    override fun getItemViewType(position: Int) = when (listOfData[position]) {
        is StartUIModel.Launches -> R.layout.launches_total
        is StartUIModel.Timer -> R.layout.timer_card
        is StartUIModel.Rockets -> R.layout.rockets_start
    }

    override fun getItemCount(): Int = listOfData.size

    fun setItems(model: List<StartUIModel>) {
        when {
            listOfData.isEmpty() -> {
                listOfData = model
                notifyItemRangeChanged(0, 2)
            }
            else -> {
                listOfData = model
                onModel.invoke(listOfData[0] as StartUIModel.Timer)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.timer_card -> TimerViewHolder(
                TimerCardBinding.bind(view),
                onClickListener,
                onModel
            )
            R.layout.launches_total -> LaunchesViewHolder(LaunchesTotalBinding.bind(view))
            R.layout.rockets_start -> RocketViewHolder(RocketsStartBinding.bind(view))
            else -> TimerViewHolder(TimerCardBinding.bind(view), onClickListener, onModel)
        }
    }

    class OnClickListener(val clickListener: () -> Unit) {
        fun onClick() = clickListener()
    }

    class OnModelChanged {
        var invoke: (model: StartUIModel.Timer) -> Unit = { }
    }
}

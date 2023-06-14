package com.nttest.ntprogresstest

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class DealsAdapter : RecyclerView.Adapter<DealsAdapter.ViewHolder>() {

    private var dealsData: List<Server.Deal> = emptyList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvInstrumentName: TextView
        val tvPrice: TextView
        val tvAmount: TextView
        val tvTimeStamp: TextView

        init {
            tvInstrumentName = view.findViewById(R.id.tvInstrumentName)
            tvPrice = view.findViewById(R.id.tvPrice)
            tvAmount = view.findViewById(R.id.tvAmount)
            tvTimeStamp = view.findViewById(R.id.tvTimeStamp)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_deal, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // viewHolder.itemView.setOnClickListener{}
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.tvInstrumentName.text = dealsData[position].instrumentName
        viewHolder.tvPrice.text = "%.2f".format(dealsData[position].price.toFloat())
        viewHolder.tvAmount.text = dealsData[position].amount.roundToInt().toString()
        viewHolder.tvTimeStamp.text = dealsData[position].timeStamp.toString()
        if (dealsData[position].side == Server.Deal.Side.SELL) {
            viewHolder.tvPrice.setTextColor(Color.parseColor("#FF0000"))
        } else {
            viewHolder.tvPrice.setTextColor(Color.parseColor("#00FF00"))
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dealsData.size

    fun setData(deals: List<Server.Deal>) {
        dealsData = deals
        notifyDataSetChanged()
    }

}
package com.nttest.ntprogresstest.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nttest.ntprogresstest.R
import com.nttest.ntprogresstest.data.Server
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

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_deal, viewGroup, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.tvInstrumentName.text = dealsData[position].instrumentName
        viewHolder.tvPrice.text = "%.2f".format(dealsData[position].price.toFloat())
        viewHolder.tvAmount.text = "Amount: ${dealsData[position].amount.roundToInt()}"
        viewHolder.tvTimeStamp.text = dealsData[position].timeStamp.toString()
        if (dealsData[position].side == Server.Deal.Side.SELL) {
            viewHolder.tvPrice.setTextColor(Color.parseColor("#BB0000"))
        } else {
            viewHolder.tvPrice.setTextColor(Color.parseColor("#009900"))
        }

    }

    override fun getItemCount() = dealsData.size

    fun setData(deals: List<Server.Deal>) {
        dealsData = deals
        notifyDataSetChanged()
    }
}
package com.nttest.ntprogresstest

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val rvDeals: RecyclerView by lazy { findViewById(R.id.rvDeals) }
    private val tvSortByTime: TextView by lazy { findViewById(R.id.tvSortingDate) }
    private val tvSortByInstrument: TextView by lazy { findViewById(R.id.tvSortingInstrument) }
    private val tvSortByPrice: TextView by lazy { findViewById(R.id.tvSortingPrice) }
    private val tvSortByAmount: TextView by lazy { findViewById(R.id.tvSortingAmount) }
    private val tvSortBySide: TextView by lazy { findViewById(R.id.tvSortingSide) }
    private val viewModel = MainViewModel()
    private val adapter: DealsAdapter by lazy { DealsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.viewState.observe(this, ::render)
        rvDeals.layoutManager = LinearLayoutManager(this)
        rvDeals.adapter = adapter

        tvSortByTime.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnDateClicked)
        }
        tvSortByInstrument.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnInstrumentClicked)
        }
        tvSortByPrice.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnPriceClicked)
        }
        tvSortByAmount.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnAmountClicked)
        }
        tvSortBySide.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnSideClicked)
        }

    }

    private fun render(viewState: ViewState) {
        adapter.setData(viewState.dealsShown)
    }
}
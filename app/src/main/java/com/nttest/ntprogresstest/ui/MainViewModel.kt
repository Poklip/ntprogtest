package com.nttest.ntprogresstest.ui

import android.util.Log
import com.nttest.ntprogresstest.base.BaseViewModel
import com.nttest.ntprogresstest.base.MyEvent
import com.nttest.ntprogresstest.data.Server
import com.nttest.ntprogresstest.domain.DealsPack

class MainViewModel : BaseViewModel<ViewState>() {

    private val dealsPack = DealsPack()
    private val server = Server()
    private var dateSortBy = true
    private var instrumentSortBy = true
    private var priceSortBy = true
    private var amountSortBy = true
    private var sideSortBy = true

    init {
        processDataEvent(DataEvent.InitLoadDeals)
    }

    override fun initialViewState() = ViewState(dealsShown = emptyList(), deals = emptyList())

    override fun reduce(event: MyEvent, previousState: ViewState): ViewState? {
        when (event) {
            is DataEvent.InitLoadDeals -> {
                server.subscribeToDeals {
                    dealsPack.addPack(it)
                    if (dealsPack.getSize() == 10) {
                        processDataEvent(DataEvent.LoadPacks(dealsPack.getPacks(10)))
                    }
                    Log.e("MAINDEBUG", "${dealsPack.getSize()}")
                    if (dealsPack.getSize() >= 1001) {
                        processDataEvent(DataEvent.LoadPacks(dealsPack.getAllPacks()))
                    }
                }
                return null
            }

            is DataEvent.LoadPacks -> {
                return previousState.copy(dealsShown = event.deals.sortedBy { it.timeStamp })
            }

            is UiEvent.OnDateClicked -> {
                if (dateSortBy) {
                    dateSortBy = !dateSortBy
                    return previousState.copy(dealsShown = previousState.dealsShown.sortedByDescending { it.timeStamp })
                } else {
                    dateSortBy = !dateSortBy
                    return previousState.copy(dealsShown = previousState.dealsShown.sortedBy { it.timeStamp })
                }
            }

            is UiEvent.OnInstrumentClicked -> {
                if (instrumentSortBy) {
                    instrumentSortBy = !instrumentSortBy
                    return previousState.copy(dealsShown = previousState.dealsShown.sortedByDescending { it.instrumentName })
                } else {
                    instrumentSortBy = !instrumentSortBy
                    return previousState.copy(dealsShown = previousState.dealsShown.sortedBy { it.instrumentName })
                }
            }

            is UiEvent.OnPriceClicked -> {
                if (priceSortBy) {
                    priceSortBy = !priceSortBy
                    return previousState.copy(dealsShown = previousState.dealsShown.sortedByDescending { it.price })
                } else {
                    priceSortBy = !priceSortBy
                    return previousState.copy(dealsShown = previousState.dealsShown.sortedBy { it.price })
                }
            }

            is UiEvent.OnAmountClicked -> {
                if (amountSortBy) {
                    amountSortBy = !amountSortBy
                    return previousState.copy(dealsShown = previousState.dealsShown.sortedByDescending { it.amount })
                } else {
                    amountSortBy = !amountSortBy
                    return previousState.copy(dealsShown = previousState.dealsShown.sortedBy { it.amount })
                }
            }

            is UiEvent.OnSideClicked -> {
                if (sideSortBy) {
                    sideSortBy = !sideSortBy
                    return previousState.copy(dealsShown = previousState.dealsShown.sortedByDescending { it.side })
                } else {
                    sideSortBy = !sideSortBy
                    return previousState.copy(dealsShown = previousState.dealsShown.sortedBy { it.side })
                }
            }

            else -> return null
        }
    }

}
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
    private var sortOwner = "timeDown"

    init {
        processDataEvent(DataEvent.InitLoadDeals)
    }

    override fun initialViewState() = ViewState(dealsShown = emptyList(), deals = emptyList())

    override fun reduce(event: MyEvent, previousState: ViewState): ViewState? {
        when (event) {
            is DataEvent.InitLoadDeals -> {
                server.subscribeToDeals {
                    dealsPack.addPack(it)
                    if (dealsPack.getSize() % 100 == 0 && dealsPack.getSize() != 0 || dealsPack.getSize() == 1) {
                        processDataEvent(DataEvent.LoadPacks(deals = dealsPack.getPacks(dealsPack.getSize())))
                    }
                    Log.e("MAINDEBUG", "${dealsPack.getSize()}")
                }
                return null
            }

            is DataEvent.LoadPacks -> {
                when (sortOwner) {
                    "timeUp" -> return previousState.copy(dealsShown = event.deals.sortedBy { it.timeStamp })
                    "timeDown" -> return previousState.copy(dealsShown = event.deals.sortedByDescending { it.timeStamp })
                    "instrumentUp" -> return previousState.copy(dealsShown = event.deals.sortedBy { it.instrumentName })
                    "instrumentDown" -> return previousState.copy(dealsShown = event.deals.sortedByDescending { it.instrumentName })
                    "priceUp" -> return previousState.copy(dealsShown = event.deals.sortedBy { it.price })
                    "priceDown" -> return previousState.copy(dealsShown = event.deals.sortedByDescending { it.price })
                    "amountUp" -> return previousState.copy(dealsShown = event.deals.sortedBy { it.amount })
                    "amountDown" -> return previousState.copy(dealsShown = event.deals.sortedByDescending { it.amount })
                    "sideUp" -> return previousState.copy(dealsShown = event.deals.sortedBy { it.side })
                    "sideDown" -> return previousState.copy(dealsShown = event.deals.sortedByDescending { it.side })
                    else -> return null
                }
            }

            is UiEvent.OnDateClicked -> {
                if (dateSortBy) {
                    dateSortBy = !dateSortBy
                    sortOwner = "timeDown"
                    return previousState.copy(dealsShown = previousState.dealsShown.sortedByDescending { it.timeStamp })
                } else {
                    dateSortBy = !dateSortBy
                    sortOwner = "timeUp"
                    return previousState.copy(dealsShown = previousState.dealsShown.sortedBy { it.timeStamp })
                }
            }

            is UiEvent.OnInstrumentClicked -> {
                if (instrumentSortBy) {
                    instrumentSortBy = !instrumentSortBy
                    sortOwner = "instrumentDown"
                    return previousState.copy(dealsShown = previousState.dealsShown.sortedByDescending { it.instrumentName })
                } else {
                    instrumentSortBy = !instrumentSortBy
                    sortOwner = "instrumentUp"
                    return previousState.copy(dealsShown = previousState.dealsShown.sortedBy { it.instrumentName })
                }
            }

            is UiEvent.OnPriceClicked -> {
                if (priceSortBy) {
                    priceSortBy = !priceSortBy
                    sortOwner = "priceDown"
                    return previousState.copy(dealsShown = previousState.dealsShown.sortedByDescending { it.price })
                } else {
                    priceSortBy = !priceSortBy
                    sortOwner = "priceUp"
                    return previousState.copy(dealsShown = previousState.dealsShown.sortedBy { it.price })
                }
            }

            is UiEvent.OnAmountClicked -> {
                if (amountSortBy) {
                    amountSortBy = !amountSortBy
                    sortOwner = "amountDown"
                    return previousState.copy(dealsShown = previousState.dealsShown.sortedByDescending { it.amount })
                } else {
                    amountSortBy = !amountSortBy
                    sortOwner = "amountUp"
                    return previousState.copy(dealsShown = previousState.dealsShown.sortedBy { it.amount })
                }
            }

            is UiEvent.OnSideClicked -> {
                if (sideSortBy) {
                    sideSortBy = !sideSortBy
                    sortOwner = "sideDown"
                    return previousState.copy(dealsShown = previousState.dealsShown.sortedByDescending { it.side })
                } else {
                    sideSortBy = !sideSortBy
                    sortOwner = "sideUp"
                    return previousState.copy(dealsShown = previousState.dealsShown.sortedBy { it.side })
                }
            }

            else -> return null
        }
    }

}